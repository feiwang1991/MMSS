package com.dianping.po;/**
 * created by IntelliJ IDEA
 */

import java.util.List;

/**
 *包装查询对象，用于包装从表示层传递到持久层的查询条件
 *author：王斐
 *create：2017-02-05  16:33
 */

public class ItemsQueryVo {
    //这里用两个items的相关类，方便进行扩展，互不影响，即可以用原始的po进行查询，也可以使用拓展的itemscustom进行查询
    private Items items;
    //一般我们需要使用这个拓展的查询类
    private ItemsCustom itemsCustom;

    //用户批量更新数据
    private List<ItemsCustom> itemsCustomList;
    public List<ItemsCustom> getItemsCustomList() {
        return itemsCustomList;
    }

    public void setItemsCustomList(List<ItemsCustom> itemsCustomList) {
        this.itemsCustomList = itemsCustomList;
    }
    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }

    public ItemsCustom getItemsCustom() {
        return itemsCustom;
    }

    public void setItemsCustom(ItemsCustom itemsCustom) {
        this.itemsCustom = itemsCustom;
    }


}
