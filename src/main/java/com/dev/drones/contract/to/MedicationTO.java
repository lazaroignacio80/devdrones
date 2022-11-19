package com.dev.drones.contract.to;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class MedicationTO {

    private long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Weight is required")
    private double weight;

    @NotBlank(message = "Serial number is required")
    private String code;

    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicationTO that = (MedicationTO) o;
        return id == that.id && Double.compare(that.weight, weight) == 0 && name.equals(that.name) && code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, weight, code);
    }
}
