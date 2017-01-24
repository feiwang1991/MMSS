package com.dianping.po;/**
 * created by IntelliJ IDEA
 */

/**
 *订单详细信息
 *author：王斐
 *create：2017-01-24  0:48
 */

public class OrderDetail {
    private int id;
    private int ordersId;
    private int itemId;
    private int itemsNum;
    //一对一对应的商品信息
    private Item item;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemsNum() {
        return itemsNum;
    }

    public void setItemsNum(int itemsNum) {
        this.itemsNum = itemsNum;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }
}
