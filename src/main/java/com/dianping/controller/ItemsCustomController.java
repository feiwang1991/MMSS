package com.dianping.controller;/**
 * created by IntelliJ IDEA
 */

import com.dianping.controller.Validator.ValidatorGroup1;
import com.dianping.po.ItemsCustom;
import com.dianping.po.ItemsQueryVo;
import com.dianping.service.ItemsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 商品的Hander,注解方式的处理器
 * 类似传统的Httrequest
 * author：王斐
 * create：2017-02-04  10:31
 */
//使用注解方式标识该类是一个处理器，比以前的实现controller接口，
// 可以写多个不同方法，而非注解实现接口方法，只能有一个方法
@Controller
//窄化url的请求路径，比如把商品相关的url全部放在/items目录下
@RequestMapping("/items")
public class ItemsCustomController {
    @Autowired
    private ItemsCustomService itemsCustomService;

    //这个方法没有requestmapping,表示把这个方法的返回值，以itemTypes为key放到request中，放到页面中去
    @ModelAttribute("itemTypes")
    public Map<String,String> getTypes(){
        Map<String,String> itemTypes=new HashMap<String, String>();
        itemTypes.put("101","母婴");
        itemTypes.put("102","保健");
        return itemTypes;
    }
    //查询商品信息
    @RequestMapping("/findItemsList")
    //@RequestParam 不加的时候，默认形参和url中参数必须匹配
    //@RequestParam 加的时候,value表示request中的名称，required表示是否在request中是必须的，defaultValue表示默认值
    //public ModelAndView findItemsList(HttpServletRequest httpServletRequest ,int id ) throws Exception {
    //public ModelAndView findItemsList(@RequestParam(value = "id",required = true,defaultValue ="1") int item_id ) throws Exception {
    public ModelAndView findItemsList(HttpServletRequest request,ItemsQueryVo itemsQueryVo) throws Exception {
        //测试转发过来，request是否保留第一次请求时候的数据
        //System.out.println(item_id);
        //System.out.println(httpServletRequest.getAttr ibute("id"));
        List<ItemsCustom> itemsList = itemsCustomService.findItemsList(itemsQueryVo);
        ModelAndView modelAndView = new ModelAndView();
        //这个方法类似request中的setAttrabute()
        modelAndView.addObject("itemsList", itemsList);
        //这里是在视图解析器中配置了路径的前缀和后缀，方面写，当然也可以写完整的路径/WEB-INF/jsp/items/itemsList.jsp
        modelAndView.setViewName("items/itemsList");
        return modelAndView;

    }

    //根据id查询商品信息，进入修改商品页面
    //限制这个url的http请求方法
    // @RequestMapping("/editItems")
    /*@RequestMapping(value="/editItems",method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView editItems() throws Exception {
        ItemsCustom itemsCustom=itemsCustomService.findItemsById(1);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("itemsCustom", itemsCustom);
        modelAndView.setViewName("items/editItems");
        return modelAndView;
    }*/
    /*
    controller一共有三种可能的返回值:
    1为ModelAndView,方法入参为空
    2为返回值为string,可以表示逻辑视图名，真正的视图（jsp全路径）=前缀+逻辑视图名+后缀，此时方法入参为Model model,用于传递model数据
    不需要我们new modelandview了，参数Model写不写也是看需要，如果需要传值到页面就写
    注意方法参数里面也可以写其他我们想要的，比如HttpServletRequest,response等，来获取其他参数。
    返回值为String时候，除了可以写逻辑视图名称，还可以写重定向和转发。
    重定向：浏览器发送两次请求，url发生变化，第二次request数据不能保存
    转发：服务器转发一次请求，url不发生变化，第二次request请求数据可以保留
    格式："redirect:url",注意url可以不写全部，如果controller有配置根路径的话,注意这些url都是另一次请求的action
         "forward:url"
    3为返回值为void,在方法形参上可以指定request和response,可以利用他们返回指定结果
    比如利用request进行转发,request.getRequestDispatcher("xxx.action").forward(req,resp);
    比如利用response进行重定向，response.sendRedirect("xxx.action")
    比如利用response指定响应结果，例如响应json数据
    response.setCharacterEncoding("utf-8")
    response.serContentType("application/json;charset=utf-8")
    response.getWriter().write("json串");
     */
    @RequestMapping(value="/editItems",method = {RequestMethod.POST,RequestMethod.GET})
    public String editItems(Model model,Integer id) throws Exception {
        ItemsCustom itemsCustom = itemsCustomService.findItemsById(id);
        //此方法类似modelandview.addObject方法
        model.addAttribute("items", itemsCustom);
        return "items/editItems";
    }


    //提交id修改商品信息
    //注意，传输pojo数据时候，表单中name必须和controller形参pojo的属性名称一致才可以顺利传入
    //若有重复的id，也是可以传入
    //在需要校验的Pojo前面添加@Validated,在需要添加校验的pojo后面添加BindingResult，用户接受校验后的出错的提示信息
    //注意上面两个校验@Validated和BindingResult必须同时出现，且顺序一前一后固定
    //@ModelAttribute可以设置Pojo数据回显到request域中，并指定key名称，默认回显pojo的方式是参数类型名称，首字母小写
    //还有一种特别好的简单的回显方式就是，使用Model，把传进来的形参，直接再赋值到Model中，Model在内部会赋值到request域中，此时不用modelattribute
    //对于简单类型的数据，注解方式是默认不支持回显，但是我们可以使用Model,model.addAttribute("id",id);
    @RequestMapping(value = "/editItemSubmit")
    public String editItemSubmit(
            Model model,HttpServletRequest httpServletRequest,
            Integer id,
            @ModelAttribute("items") @Validated(value = {ValidatorGroup1.class}) ItemsCustom itemsCustom ,
            BindingResult bindingResult,
            MultipartFile items_pic) throws Exception {
        if(bindingResult.hasErrors()){
            List<ObjectError> objectErrors=bindingResult.getAllErrors();
            for (ObjectError objectError : objectErrors) {
                System.out.println(objectError.getDefaultMessage());
            }
            //校验到错误之后，需要把错误信息传递到前端页面，可以通过model来传递参数,并继续返回编辑页面，同时显示错误
            model.addAttribute("objectErrors", objectErrors);
            //model.addAttribute("items",itemsCustom);回显数据，这种方式特别简单使用
            //model.addAttribute("id",id);可以使用Model完成对简单类型的数据回显，原理是Model本身是一个HashMap，value可以放pojo类数据，也可以放
            //简单类型数据，key都是string而已
            return "items/editItems";
        }
        if(items_pic!=null && items_pic.getOriginalFilename()!=null && items_pic.getOriginalFilename().length()>0){
            //接收上传的图片，并且存到本地服务器上的图片路径中
            //设置本地图片服务器图片存放的物理地址
            String pic_path="E:\\tomcat\\tomcatPic\\temp\\";
            //找到长传的图片名称，主要为了获取图片的格式
            String pic_name=items_pic.getOriginalFilename();
            //根据存放的物理地址和图片名称（获得格式），进而得到图片存放的新的完整路径
            //图片名称一般为了不重复，可以设置为UUID
            String pic_NewName=UUID.randomUUID()+pic_name.substring(pic_name.lastIndexOf("."));
            String pic_NewPath=pic_path+pic_NewName;
            File file=new File(pic_NewPath);
            items_pic.transferTo(file);//把内存中的图片，通过file进行持久化
            //注意：不要忘记在数据库中，设置图片的名称，只存名称即可，真实图片放在服务器目录中
            itemsCustom.setPic(pic_NewName);
        }
        itemsCustomService.updateItemsById(id,itemsCustom);
        //return "redirect:findItemsList.action";
        //return "forward:findItemsList.action";//注意这里也是写配置的根路径下面的路径，注意这里不写/
        return "success";
    }
    //注意：这里使用数组进行传参数，进行批量删除，checkbox里面的name都是一样的，都是itemsId，这样才好映射到同一个数组中
    @RequestMapping("/deleteItems")
    public String deleteItems(HttpServletRequest httpServletRequest,Integer[] itemsId)throws Exception{
        //调用service删除
        return "success";
    }
    //进入商品修改页面
    @RequestMapping("/editItemQuery")
    public ModelAndView editItemQuery(HttpServletRequest request) throws Exception {
        List<ItemsCustom> itemsList = itemsCustomService.findItemsList(null);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("itemsList", itemsList);
        modelAndView.setViewName("items/editItemQuery");
        return modelAndView;
    }
    //提交所有商品
    //注意：传递list参数对所有商品进行修改提交的时候,list一定要被包含在包装类(vo)中,只有这样，才好在页面中
    //对每个Input标签的name使用 list[i].name  list[i].price等等
    //传入map形式的参数和List类似，也是在包装类中加入map，在页面上使用<input type="text" name="map['name']" value="${xxx}"/>就可以映射
    @RequestMapping("/editItemAllSubmit")
    public String editItemAllSubmit(ItemsQueryVo itemsQueryVo) throws Exception {

        return "success";
    }

}
