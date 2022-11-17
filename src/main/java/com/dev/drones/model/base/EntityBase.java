package com.dev.drones.model.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class EntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    protected long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }
}
