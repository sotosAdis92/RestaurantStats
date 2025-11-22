package org.example.databasetechnologyproject;

public class OrderItem {
    private int orderitemid;
    private int orderid;
    private int itemid;
    private int quantity;
    private float price;

    public OrderItem(int orderitemid, int orderid, int itemid, int quantity, float price) {
        this.orderitemid = orderitemid;
        this.orderid = orderid;
        this.itemid = itemid;
        this.quantity = quantity;
        this.price = price;
    }

    public void setOrderitemid(int orderitemid) {
        this.orderitemid = orderitemid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getOrderitemid() {
        return orderitemid;
    }

    public int getOrderid() {
        return orderid;
    }

    public int getItemid() {
        return itemid;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }
}

