package org.example.databasetechnologyproject;

public class Tables {
    private int tid;
    private int tnumber;
    private int capacity;
    private String status;

    public Tables(int tid, int tnumber, int capacity, String status) {
        this.tid = tid;
        this.tnumber = tnumber;
        this.capacity = capacity;
        this.status = status;
    }

    public int getTid() {
        return tid;
    }

    public int getTnumber() {
        return tnumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public void setTnumber(int tnumber) {
        this.tnumber = tnumber;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
