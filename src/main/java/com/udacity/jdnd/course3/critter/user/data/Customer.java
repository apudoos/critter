package com.udacity.jdnd.course3.critter.user.data;

public class Customer {
    Long id;
    String name;
    String phone;
    String notes;

    public Customer(Long id, String name, String phone, String notes) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.notes = notes;
    }

    public Customer() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
