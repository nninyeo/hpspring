package com.min.hpspring.ioc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class Pork {

    private String name;

    public Pork(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
