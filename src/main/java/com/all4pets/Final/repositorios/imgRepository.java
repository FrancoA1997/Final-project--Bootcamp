package com.all4pets.Final.repositorios;

import com.all4pets.Final.entidades.imgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface imgRepository extends JpaRepository<imgEntity, String> {
    
    
}