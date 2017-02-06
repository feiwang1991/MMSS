package com.dianping.po;/**
 * created by IntelliJ IDEA
 */

/**
 *包装查询对象，用于包装从表示层传递到持久层的查询条件
 *author：王斐
 *create：2017-02-05  16:33
 */

public class ItemsQueryVo {
    private Items items;
    //一般我们需要使用这个拓展的查询类
    private ItemsCustom itemsCustom;

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
