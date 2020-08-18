package com.udacity.jdnd.course3.critter.user.data;

import java.util.List;

//Simple class to get customer and pet Id.
public class CustomerData {
    Long id;
    String name;
    String phone;
    String notes;
    private List<Long> petIds;

    public CustomerData(Long id, String name, String phone, String notes, List<Long> petIds) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.notes = notes;
        this.petIds = petIds;
    }

    public CustomerData() {}

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

    public List<Long> getPetIds() {
        return petIds;
    }

    public void setPetIds(List<Long> petIds) {
        this.petIds = petIds;
    }

    @Override
    public String toString() {
        return "CustomerData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", notes='" + notes + '\'' +
                ", petIds=" + petIds +
                '}';
    }
}
