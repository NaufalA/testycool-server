package com.testycool.testycoolserver.features.participant.entities;

import com.testycool.testycoolserver.shared.entities.SerialBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "participants")
public class Participant extends SerialBaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}