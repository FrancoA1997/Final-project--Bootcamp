package com.all4pets.Final.enumeraciones;


public enum type {

    FOOD("FOOD"),
    ACCESSORIES("ACCESSORIES"),
    TOYS("TOYS"),
    MEDICINE("MEDICINE");
    
    private String type;

   private type(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

   
   
}
