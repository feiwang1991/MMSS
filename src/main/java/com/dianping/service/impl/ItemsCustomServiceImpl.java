package com.dianping.service.impl;/**
 * created by IntelliJ IDEA
 */

import com.dianping.mapper.ItemsCustomMapper;
import com.dianping.mapper.ItemsMapper;
import com.dianping.po.Items;
import com.dianping.po.ItemsCustom;
import com.dianping.po.ItemsQueryVo;
import com.dianping.service.ItemsCustomService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * itemscustomservice的实现类
 * author：王斐
 * create：2017-02-05  17:46
 */

public class ItemsCustomServiceImpl implements ItemsCustomService {
    //applicationContext-dao中的mapper扫描方式，已经自动在spring容器中注册了mapper的代理类（dao的实现类）
    @Autowired
    private ItemsCustomMapper itemsCustomMapper;
    @Autowired
    private ItemsMapper itemsMapper;


    public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception {
        return itemsCustomMapper.findItemsList(itemsQueryVo);
    }

    public ItemsCustom findItemsById(Integer id) throws Exception {
        //注意在根据id查询商品时候，先验证id是否为空，为空需要抛出异常，这是良好的编码习惯
        Items items=itemsMapper.selectByPrimaryKey(id);
        //为了展示效果更过些，通常需要使用拓展的ItemsCustom类进行传输展示，使用spring自带的属性复制工具类
        ItemsCustom itemsCustom=new ItemsCustom();
        BeanUtils.copyProperties(items,itemsCustom);
        return itemsCustom;
    }

    public void updateItemsById(Integer id, ItemsCustom itemsCustom) throws Exception {
        //注意在根据id查询商品时候，先验证id是否为空，为空需要抛出异常，这是良好的编码习惯
        //因为修改时候,因为有一些大的数据类型,这里是text，选择blob相关的
        //因为根据id该，最好设置id
        itemsCustom.setId(id);
        itemsMapper.updateByPrimaryKeyWithBLOBs(itemsCustom);
    }
}
