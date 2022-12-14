package com.testycool.testycoolserver.shared.entities;

import javax.persistence.*;

@MappedSuperclass
public class SerialBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}