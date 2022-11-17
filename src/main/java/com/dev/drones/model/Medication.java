package com.dev.drones.model;

import com.dev.drones.model.base.EntityBase;
import com.dev.drones.model.type.Model;
import com.dev.drones.model.type.State;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "medications")
public class Medication extends EntityBase {

    @Column(name = "name")
    private String name;

    @Column(name = "weight")
    private double weight;

    @Column(name = "code")
    private String code;

    @Column(name = "image")
    private String image;

    public Medication() {
        super();
    }

    public Medication(String name,
                      double weight,
                      String code,
                      String image) {
        super();
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Medication [id=" + this.getId() + ", name=" + name + ", weight=" + weight + ", code=" + code + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return Double.compare(that.weight, weight) == 0 && name.equals(that.name) && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight, code);
    }
}
