package org.example.databasetechnologyproject;

public class EmployeeAudit {
    private String operation;
    private String timestamp;
    private String userid;
    private int id;
    private String new_first_name;
    private String old_first_name;
    private String new_last_name;
    private String old_last_name;
    private String new_pos;
    private String old_pos;
    private String new_email;
    private String old_email;
    private int new_salary;
    private int old_salary;

    public EmployeeAudit(String operation, String timestamp, String userid, int id, String new_first_name, String old_first_name, String new_last_name, String old_last_name, String new_pos, String old_pos, String new_email, String old_email, int new_salary, int old_salary) {
        this.operation = operation;
        this.timestamp = timestamp;
        this.userid = userid;
        this.id = id;
        this.new_first_name = new_first_name;
        this.old_first_name = old_first_name;
        this.new_last_name = new_last_name;
        this.old_last_name = old_last_name;
        this.new_pos = new_pos;
        this.old_pos = old_pos;
        this.new_email = new_email;
        this.old_email = old_email;
        this.new_salary = new_salary;
        this.old_salary = old_salary;
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

    public String getOld_first_name() {
        return old_first_name;
    }

    public String getNew_last_name() {
        return new_last_name;
    }

    public String getNew_first_name() {
        return new_first_name;
    }

    public String getOld_last_name() {
        return old_last_name;
    }

    public String getNew_pos() {
        return new_pos;
    }

    public String getOld_pos() {
        return old_pos;
    }

    public String getNew_email() {
        return new_email;
    }

    public String getOld_email() {
        return old_email;
    }

    public int getNew_salary() {
        return new_salary;
    }

    public int getOld_salary() {
        return old_salary;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setNew_first_name(String new_first_name) {
        this.new_first_name = new_first_name;
    }

    public void setNew_last_name(String new_last_name) {
        this.new_last_name = new_last_name;
    }

    public void setOld_last_name(String old_last_name) {
        this.old_last_name = old_last_name;
    }

    public void setOld_first_name(String old_first_name) {
        this.old_first_name = old_first_name;
    }

    public void setNew_pos(String new_pos) {
        this.new_pos = new_pos;
    }

    public void setNew_email(String new_email) {
        this.new_email = new_email;
    }

    public void setOld_salary(int old_salary) {
        this.old_salary = old_salary;
    }

    public void setNew_salary(int new_salary) {
        this.new_salary = new_salary;
    }

    public void setOld_email(String old_email) {
        this.old_email = old_email;
    }

    public void setOld_pos(String old_pos) {
        this.old_pos = old_pos;
    }
}
