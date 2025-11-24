package org.example.databasetechnologyproject;

import java.sql.Timestamp;

public class OrderAudit {
    private String operation;
    private String time;
    private String user;
    private int id;
    private String newemp;
    private String oldemp;
    private int newta;
    private int oldta;
    private float newto;
    private float oldto;
    private Timestamp nDate;
    private Timestamp oDate;

    public OrderAudit(String operation, String time, String user, String newemp, String oldemp, int newta, int oldta, float newto, float oldto, Timestamp nDate, Timestamp oDate) {
        this.operation = operation;
        this.time = time;
        this.user = user;
        this.newemp = newemp;
        this.oldemp = oldemp;
        this.newta = newta;
        this.oldta = oldta;
        this.newto = newto;
        this.oldto = oldto;
        this.nDate = nDate;
        this.oDate = oDate;
    }

    public OrderAudit(String operation, String time, String user, int id, String newemp, String oldemp, int newta, int oldta, float newto, float oldto, Timestamp nDate, Timestamp oDate) {
        this.operation = operation;
        this.time = time;
        this.user = user;
        this.id = id;
        this.newemp = newemp;
        this.oldemp = oldemp;
        this.newta = newta;
        this.oldta = oldta;
        this.newto = newto;
        this.oldto = oldto;
        this.nDate = nDate;
        this.oDate = oDate;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setNewemp(String newemp) {
        this.newemp = newemp;
    }

    public void setOldemp(String oldemp) {
        this.oldemp = oldemp;
    }

    public void setNewta(int newta) {
        this.newta = newta;
    }

    public void setOldta(int oldta) {
        this.oldta = oldta;
    }

    public void setNewto(float newto) {
        this.newto = newto;
    }

    public void setOldto(float oldto) {
        this.oldto = oldto;
    }

    public void setnDate(Timestamp nDate) {
        this.nDate = nDate;
    }

    public void setoDate(Timestamp oDate) {
        this.oDate = oDate;
    }

    public String getOperation() {
        return operation;
    }

    public String getTime() {
        return time;
    }

    public String getUser() {
        return user;
    }

    public String getNewemp() {
        return newemp;
    }

    public String getOldemp() {
        return oldemp;
    }

    public int getNewta() {
        return newta;
    }

    public int getOldta() {
        return oldta;
    }

    public float getNewto() {
        return newto;
    }

    public float getOldto() {
        return oldto;
    }

    public Timestamp getNDate() {
        return nDate;
    }

    public Timestamp getODate() {
        return oDate;
    }
}
