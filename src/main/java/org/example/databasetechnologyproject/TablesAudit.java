package org.example.databasetechnologyproject;

public class TablesAudit {
    private String operation;
    private String timestamp;
    private String userid;
    private int id;
    private int new_tablenumber;
    private int old_tablenumber;
    private int new_capacity;
    private int old_capacity;
    private String new_status;
    private String old_status;

    public TablesAudit(String operation, String timestamp, String userid, int id, int new_tablenumber, int old_tablenumber, int new_capacity, int old_capacity, String new_status, String old_status) {
        this.operation = operation;
        this.timestamp = timestamp;
        this.userid = userid;
        this.id = id;
        this.new_tablenumber = new_tablenumber;
        this.old_tablenumber = old_tablenumber;
        this.new_capacity = new_capacity;
        this.old_capacity = old_capacity;
        this.new_status = new_status;
        this.old_status = old_status;
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

    public int getNew_tablenumber() {
        return new_tablenumber;
    }

    public int getOld_tablenumber() {
        return old_tablenumber;
    }

    public int getNew_capacity() {
        return new_capacity;
    }

    public int getOld_capacity() {
        return old_capacity;
    }

    public String getNew_status() {
        return new_status;
    }

    public String getOld_status() {
        return old_status;
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

    public void setNew_tablenumber(int new_tablenumber) {
        this.new_tablenumber = new_tablenumber;
    }

    public void setOld_tablenumber(int old_tablenumber) {
        this.old_tablenumber = old_tablenumber;
    }

    public void setNew_capacity(int new_capacity) {
        this.new_capacity = new_capacity;
    }

    public void setOld_capacity(int old_capacity) {
        this.old_capacity = old_capacity;
    }

    public void setNew_status(String new_status) {
        this.new_status = new_status;
    }

    public void setOld_status(String old_status) {
        this.old_status = old_status;
    }
}
