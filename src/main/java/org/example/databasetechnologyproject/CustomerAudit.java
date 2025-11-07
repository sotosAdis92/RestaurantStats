package org.example.databasetechnologyproject;

public class CustomerAudit {
    private String operation;
    private String timestamp;
    private String userid;
    private int id;
    private String new_first_name;
    private String old_first_name;
    private String new_last_name;
    private String old_last_name;
    private String new_address;
    private String old_address;
    private String new_phone;
    private String old_phone;
    private int new_rating;
    private int old_rating;

    public CustomerAudit(String operation, String timestamp, String userid, int id, String new_first_name, String old_first_name, String new_last_name, String old_last_name, String new_address, String old_address, String new_phone, String old_phone, int new_rating, int old_rating) {
        this.operation = operation;
        this.timestamp = timestamp;
        this.userid = userid;
        this.id = id;
        this.new_first_name = new_first_name;
        this.old_first_name = old_first_name;
        this.new_last_name = new_last_name;
        this.old_last_name = old_last_name;
        this.new_address = new_address;
        this.old_address = old_address;
        this.new_phone = new_phone;
        this.old_phone = old_phone;
        this.new_rating = new_rating;
        this.old_rating = old_rating;
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

    public void setNew_first_name(String new_first_name) {
        this.new_first_name = new_first_name;
    }

    public void setOld_first_name(String old_first_name) {
        this.old_first_name = old_first_name;
    }

    public void setNew_last_name(String new_last_name) {
        this.new_last_name = new_last_name;
    }

    public void setOld_last_name(String old_last_name) {
        this.old_last_name = old_last_name;
    }

    public void setNew_address(String new_address) {
        this.new_address = new_address;
    }

    public void setOld_address(String old_address) {
        this.old_address = old_address;
    }

    public void setNew_phone(String new_phone) {
        this.new_phone = new_phone;
    }

    public void setOld_phone(String old_phone) {
        this.old_phone = old_phone;
    }

    public void setNew_rating(int new_rating) {
        this.new_rating = new_rating;
    }

    public void setOld_rating(int old_rating) {
        this.old_rating = old_rating;
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

    public String getNew_first_name() {
        return new_first_name;
    }

    public String getOld_first_name() {
        return old_first_name;
    }

    public String getNew_last_name() {
        return new_last_name;
    }

    public String getOld_last_name() {
        return old_last_name;
    }

    public String getNew_address() {
        return new_address;
    }

    public String getOld_address() {
        return old_address;
    }

    public String getNew_phone() {
        return new_phone;
    }

    public String getOld_phone() {
        return old_phone;
    }

    public int getNew_rating() {
        return new_rating;
    }

    public int getOld_rating() {
        return old_rating;
    }
}
