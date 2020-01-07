package com.practice.machineservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Machine {

    @Id
    @GeneratedValue
    private Long Id;
    private String name;
    private String description;
    private int throughputMins;

    public Machine(String name, String description, int throughputMins) {
        this.name = name;
        this.description = description;
        this.throughputMins = throughputMins;
    }

    public Machine() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getThroughputMins() {
        return throughputMins;
    }

    public void setThroughputMins(int throughputMins) {
        this.throughputMins = throughputMins;
    }
}
