package com.alm.test;

import java.util.UUID;

public class SimpleTestObject {
    private String id = UUID.randomUUID().toString();
    private int value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void increment() {
        this.value++;
    }
}
