package com.all4pets.Final.enumeraciones;

public enum gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private String gender;

    private gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }
}
