
package com.all4pets.Final.enumeraciones;


public enum age {
    
    ADULT("ADULT"),
    MIDAGE("MIDAGE"),
    CHILD("CHILD");
    
    private String age;

    private age(String age) {
        this.age = age;
    }

    public String getAge() {
        return this.age;
    }
    
}
