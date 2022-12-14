package com.all4pets.Final.repositorios;

import com.all4pets.Final.entidades.petEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface petRepository extends JpaRepository<petEntity, String> {
    
    
}