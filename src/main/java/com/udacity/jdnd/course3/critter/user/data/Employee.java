package com.udacity.jdnd.course3.critter.user.data;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

//This Entity uses Spring Data to retrieve the data from the Employee table.
//Uses EmployeeSkill and DayOfWeek as @ElementCollection.

@Entity
@Table(name="Employee")
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ElementCollection(targetClass=EmployeeSkill.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="EmployeeSkill")
    @Column(name="skills")
    private Set<EmployeeSkill> skills;

    @ElementCollection(targetClass=DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="DayOfWeek")
    @Column(name="daysAvailable")
    private Set<DayOfWeek> daysAvailable;

    public Employee() {}

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

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }
}
