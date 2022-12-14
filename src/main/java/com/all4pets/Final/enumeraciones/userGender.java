
package com.all4pets.Final.enumeraciones;


public enum userGender {
    FEMALE("FEMALE"),
    MALE("MALE");

    private String userGender;

    private userGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserGender() {
        return this.userGender;
    }
    
}
