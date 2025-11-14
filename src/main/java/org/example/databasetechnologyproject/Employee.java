package org.example.databasetechnologyproject;

public class Employee {
    private int empid;
    private String firstName;
    private String lastName;
    private int salary;
    private String email;
    private String position;

    public Employee(int empid, String firstName, String lastName, String position, int salary, String email) {
        this.empid = empid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.salary = salary;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public int getEmpid() {
        return empid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPosition() {
        return position;
    }

    public int getSalary() {
        return salary;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
