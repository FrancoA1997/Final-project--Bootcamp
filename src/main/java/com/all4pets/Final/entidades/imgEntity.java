package com.all4pets.Final.entidades;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Data
@Entity
public class imgEntity {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private String imageName;
    private String mime;
    
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] content;

}