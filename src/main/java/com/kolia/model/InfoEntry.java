package com.kolia.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Table(name = "info_entry")
@Entity
public class InfoEntry {

    @Column
    @GeneratedValue
    @Id
    private Integer id;

    @Column
    private Integer electricityPerDay;

    @Column
    private String month;

    @Column
    private Integer numberOfTheDay;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appliance_fk")
    @JsonIgnore
    private Appliance appliance;

    @JsonBackReference
    public Appliance getAppliance() {
        return appliance;
    }

    public void setAppliance(Appliance appliance) {
        this.appliance = appliance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberOfTheDay() {
        return numberOfTheDay;
    }

    public void setNumberOfTheDay(Integer numberOfTheDay) {
        this.numberOfTheDay = numberOfTheDay;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getElectricityPerDay() {
        return electricityPerDay;
    }

    public void setElectricityPerDay(Integer electricityPerDay) {
        this.electricityPerDay = electricityPerDay;
    }
}
