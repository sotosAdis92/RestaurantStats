package org.example.databasetechnologyproject;

import java.sql.Timestamp;

public class ReservationAudit {
    private String operation;
    private String timestamp;
    private String userid;
    private int id;
    private int newcid;
    private int oldcid;
    private int newtid;
    private int oldtid;
    private Timestamp newtime;
    private Timestamp oldtime;
    private int newps;
    private int oldps;

    public ReservationAudit(String operation, String timestamp, String userid, int id, int newcid, int oldcid, int newtid, int oldtid, Timestamp newtime, Timestamp oldtime, int newps, int oldps) {
        this.operation = operation;
        this.timestamp = timestamp;
        this.userid = userid;
        this.id = id;
        this.newcid = newcid;
        this.oldcid = oldcid;
        this.newtid = newtid;
        this.oldtid = oldtid;
        this.newtime = newtime;
        this.oldtime = oldtime;
        this.newps = newps;
        this.oldps = oldps;
    }

    public String getOperation() {
        return operation;
    }

    public int getId() {
        return id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUserid() {
        return userid;
    }

    public int getNewcid() {
        return newcid;
    }

    public int getOldcid() {
        return oldcid;
    }

    public int getNewtid() {
        return newtid;
    }

    public int getOldtid() {
        return oldtid;
    }

    public Timestamp getNewtime() {
        return newtime;
    }

    public Timestamp getOldtime() {
        return oldtime;
    }

    public int getNewps() {
        return newps;
    }

    public int getOldps() {
        return oldps;
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

    public void setNewcid(int newcid) {
        this.newcid = newcid;
    }

    public void setOldcid(int oldcid) {
        this.oldcid = oldcid;
    }

    public void setNewtid(int newtid) {
        this.newtid = newtid;
    }

    public void setOldtid(int oldtid) {
        this.oldtid = oldtid;
    }

    public void setNewtime(Timestamp newtime) {
        this.newtime = newtime;
    }

    public void setOldtime(Timestamp oldtime) {
        this.oldtime = oldtime;
    }

    public void setNewps(int newps) {
        this.newps = newps;
    }

    public void setOldps(int oldps) {
        this.oldps = oldps;
    }
}
