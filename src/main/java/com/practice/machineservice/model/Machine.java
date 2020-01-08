package com.practice.machineservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
public class Machine {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(max = 40, message = "name must be less than/equal to 40 characters")
    private String name;
    private String description;

    @Positive(message = "throughput per minute must be a positive integer")
    private Integer throughputMins;

    public Machine(String name, String description, int throughputMins) {
        this.name = name;
        this.description = description;
        this.throughputMins = throughputMins;
    }

    public Machine(Long id, Machine input) {
        this.id = id;
        this.name = input.name;
        this.description = input.description;
        this.throughputMins = input.throughputMins;
    }


    public Machine(Long id, String name, String description, int throughputMins) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.throughputMins = throughputMins;
    }

    public Machine() {
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getThroughputMins() {
        return throughputMins;
    }

    public void setThroughputMins(int throughputMins) {
        this.throughputMins = throughputMins;
    }


    @Override
    public String toString() {
        return "Machine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", throughputMins=" + throughputMins +
                '}';
    }
}
