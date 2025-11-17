package com.ave.Architecture.Validation.Engine.AVE.model;
import jakarta.persistence.*;



@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String ownerId; // Employee ID


    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Service() {
    }

    public Service( String name, String ownerId) {

        this.ownerId = ownerId;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

}
