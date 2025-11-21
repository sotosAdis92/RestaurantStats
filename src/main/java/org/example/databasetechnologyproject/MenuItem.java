package org.example.databasetechnologyproject;

public class MenuItem {
    private int itemid;
    private String itemname;
    private float price;
    private String category;
    private String state;

    public MenuItem(int itemid, String itemname, float price, String category, String state) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.price = price;
        this.category = category;
        this.state = state;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getItemid() {
        return itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public float getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getState() {
        return state;
    }
}
