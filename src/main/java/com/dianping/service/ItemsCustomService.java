package com.dianping.service;

import com.dianping.po.ItemsCustom;
import com.dianping.po.ItemsQueryVo;

import java.util.List;

/**
 * created by IntelliJ IDEA
 */
public interface ItemsCustomService {
    //查询商品信息
    public List<ItemsCustom> findItemsList(ItemsQueryVo itemsQueryVo) throws Exception;

    //根据id，查询商品信息，注意返回的商品类为ItemsCustom，因为在展示的时候，可能在service中做一些扩展
    public ItemsCustom findItemsById(Integer id) throws Exception;

    //根据id,修改商品信息,注意为了规范，最好提供Id参数，让人一看便知
    public void updateItemsById(Integer id,ItemsCustom itemsCustom) throws Exception;
}
