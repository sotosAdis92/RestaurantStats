package org.example.databasetechnologyproject;

public class MenuAudti {
    private String operation;
    private String timestamp;
    private String userid;
    private int id;
    private String new_name;
    private String old_name;
    private float new_price;
    private float old_price;
    private String new_category;
    private String old_category;
    private String new_state;
    private String old_state;

    public MenuAudti(String operation, String timestamp, String userid, int id, String new_name, String old_name, float new_price, float old_price, String new_category, String old_category, String new_state, String old_state) {
        this.operation = operation;
        this.timestamp = timestamp;
        this.userid = userid;
        this.id = id;
        this.new_name = new_name;
        this.old_name = old_name;
        this.new_price = new_price;
        this.old_price = old_price;
        this.new_category = new_category;
        this.old_category = old_category;
        this.new_state = new_state;
        this.old_state = old_state;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNew_name(String new_name) {
        this.new_name = new_name;
    }

    public void setOld_name(String old_name) {
        this.old_name = old_name;
    }

    public void setNew_price(float new_price) {
        this.new_price = new_price;
    }

    public void setOld_price(float old_price) {
        this.old_price = old_price;
    }

    public void setNew_category(String new_category) {
        this.new_category = new_category;
    }

    public void setOld_category(String old_category) {
        this.old_category = old_category;
    }

    public void setNew_state(String new_state) {
        this.new_state = new_state;
    }

    public void setOld_state(String old_state) {
        this.old_state = old_state;
    }

    public String getOperation() {
        return operation;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUserid() {
        return userid;
    }

    public int getId() {
        return id;
    }

    public String getNew_name() {
        return new_name;
    }

    public String getOld_name() {
        return old_name;
    }

    public float getNew_price() {
        return new_price;
    }

    public float getOld_price() {
        return old_price;
    }

    public String getNew_category() {
        return new_category;
    }

    public String getOld_category() {
        return old_category;
    }

    public String getNew_state() {
        return new_state;
    }

    public String getOld_state() {
        return old_state;
    }
}
