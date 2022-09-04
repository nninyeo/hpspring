package com.min.hpspring.ioc;

public abstract class Ingredient {
    public Ingredient(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private String name;
}
