package org.example.databasetechnologyproject;

import java.sql.Timestamp;

public class Order {
    private int orderid;
    private int customerid;
    private int employeeid;
    private int tableid;
    private float total;
    private Timestamp date;

    public Order(int orderid, int customerid, int employeeid, int tableid, float total, Timestamp date) {
        this.orderid = orderid;
        this.customerid = customerid;
        this.employeeid = employeeid;
        this.tableid = tableid;
        this.total = total;
        this.date = date;
    }

    public int getOrderid() {
        return orderid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public int getEmployeeid() {
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

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public void setEmployeeid(int employeeid) {
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
