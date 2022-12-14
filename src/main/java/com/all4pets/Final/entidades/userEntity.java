package com.all4pets.Final.entidades;

import com.all4pets.Final.enumeraciones.Rol;
import com.all4pets.Final.enumeraciones.userGender;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class userEntity {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(nullable = false)
    private String userName;

    @OneToOne
    private imgEntity imgEntity;
    
    @Enumerated(EnumType.STRING)
    private userGender userGender;
    
    private Integer age;
    
    @OneToOne
    private petEntity petEntity;
    
    @Column(unique = true)
    private String email;
    
    @Column(nullable = false)
    private String psw;
    
    private String phone;
    private String address;
    private Boolean alta;

    @Enumerated(EnumType.STRING)
    private Rol rol;

}