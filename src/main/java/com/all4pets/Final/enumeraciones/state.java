package com.all4pets.Final.enumeraciones;

public enum state {

    LOST("LOST"),
    ADOPTED("ADOPTED");

    private String state;

    private state(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}