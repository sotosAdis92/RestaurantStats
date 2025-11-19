package org.example.databasetechnologyproject;

import java.sql.Timestamp;

public class Order {
    private int orderid;
    private String customer;
    private String employeeid;
    private int tableid;
    private float total;
    private Timestamp date;

    public Order(int orderid, String customer, String employeeid, int tableid, float total, Timestamp date) {
        this.orderid = orderid;
        this.customer = customer;
        this.employeeid = employeeid;
        this.tableid = tableid;
        this.total = total;
        this.date = date;
    }

    public int getOrderid() {
        return orderid;
    }

    public String getCustomer() {
        return customer;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public int getTableid() {
        return tableid;
    }

    public float getTotal() {
        return total;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
