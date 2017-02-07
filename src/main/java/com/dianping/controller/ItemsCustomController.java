package com.dianping.controller;/**
 * created by IntelliJ IDEA
 */

import com.dianping.po.Items;
import com.dianping.po.ItemsCustom;
import com.dianping.po.ItemsQueryVo;
import com.dianping.service.ItemsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        model.addAttribute("itemsCustom", itemsCustom);
        return "items/editItems";
    }


    //提交id修改商品信息
    //注意，传输pojo数据时候，表单中name必须和controller形参pojo的属性名称一致才可以顺利传入
    //若有重复的id，也是可以传入
    //在需要校验的Pojo前面添加@Validated,在需要添加校验的pojo后面添加BindingResult，用户接受校验后的出错的提示信息
    //注意上面两个校验@Validated和BindingResult必须同时出现，且顺序一前一后固定
    @RequestMapping(value = "/editItemSubmit")
    public String editItemSubmit(Model model,HttpServletRequest httpServletRequest,Integer id,
                                 @Validated ItemsCustom itemsCustom ,BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            List<ObjectError> objectErrors=bindingResult.getAllErrors();
            for (ObjectError objectError : objectErrors) {
                System.out.println(objectError.getDefaultMessage());
            }
            //校验到错误之后，需要把错误信息传递到前端页面，可以通过model来传递参数,并继续返回编辑页面，同时显示错误
            model.addAttribute("objectErrors", objectErrors);
            return "items/editItems";
        }
        itemsCustomService.updateItemsById(id,itemsCustom);
        //return "redirect:findItemsList.action";
        //return "forward:findItemsList.action";//注意这里也是写配置的根路径下面的路径，注意这里不写/
        return "items/success";
    }
    //注意：这里使用数组进行传参数，进行批量删除，checkbox里面的name都是一样的，都是itemsId，这样才好映射到同一个数组中
    @RequestMapping("/deleteItems")
    public String deleteItems(HttpServletRequest httpServletRequest,Integer[] itemsId)throws Exception{
        //调用service删除
        return "items/success";
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

        return "items/success";
    }

}
