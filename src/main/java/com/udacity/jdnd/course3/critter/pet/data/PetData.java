package com.udacity.jdnd.course3.critter.pet.data;

import java.time.LocalDate;

//Used jdbcTemplate to retrieve pet data. Pet data is a simple class. So decided to use jdbcTemplate.
public class PetData {
    private Long petId;
    private String type;
    private String name;
    private Long ownerId;
    private LocalDate birthDate;
    private String notes;

    public PetData() {
    }

    public PetData(Long petId, String type, String name, Long ownerId, LocalDate birthDate, String notes) {
        this.petId = petId;
        this.type = type;
        this.name = name;
        this.ownerId = ownerId;
        this.birthDate = birthDate;
        this.notes = notes;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "PetData{" +
                "petId=" + petId +
                ", petType='" + type + '\'' +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId +
                ", birthDate=" + birthDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}
