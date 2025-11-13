package org.example.databasetechnologyproject;

import java.sql.Timestamp;

public class Reservation {
    private int reservationid;
    private int customerid;
    private int tableid;
    private Timestamp reservationtime;
    private int party_size;

    public Reservation(int reservationid, int customerid, int tableid, Timestamp reservationtime, int party_size) {
        this.reservationid = reservationid;
        this.customerid = customerid;
        this.tableid = tableid;
        this.reservationtime = reservationtime;
        this.party_size = party_size;
    }

    public int getReservationid() {
        return reservationid;
    }

    public int getCustomerid() {
        return customerid;
    }

    public int getTableid() {
        return tableid;
    }

    public Timestamp getReservationtime() {
        return reservationtime;
    }

    public int getParty_size() {
        return party_size;
    }

    public void setReservationid(int reservationid) {
        this.reservationid = reservationid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public void setTableid(int tableid) {
        this.tableid = tableid;
    }

    public void setReservationtime(Timestamp reservationtime) {
        this.reservationtime = reservationtime;
    }

    public void setParty_size(int party_size) {
        this.party_size = party_size;
    }
}
