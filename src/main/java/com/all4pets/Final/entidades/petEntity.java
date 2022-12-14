package com.all4pets.Final.entidades;

import com.all4pets.Final.enumeraciones.state;

import javax.persistence.*;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class petEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    @OneToOne
    @Lob
    private imgEntity imgEntity;
  
    
    private String userId;
    private String type; //Clase de animal, ya sea perro, gato, pato, lo que sea
    private String observations;
    
    @Enumerated (EnumType.STRING)
    private state state; //Cómo se encuentra esa mascota
    
    private Boolean alta;
    
}