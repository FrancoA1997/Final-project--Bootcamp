package com.all4pets.Final.entidades;

import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class productEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String productName;
    
    
  
    
    private Integer price;
    private Boolean stock;
    private String description;

}