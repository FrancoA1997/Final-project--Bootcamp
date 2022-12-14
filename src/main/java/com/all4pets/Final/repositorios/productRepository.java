package com.all4pets.Final.repositorios;

import com.all4pets.Final.entidades.productEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface productRepository extends JpaRepository<productEntity, String> {
    @Query("SELECT u FROM productEntity u WHERE u.id = :id")
   public productEntity findProductById(@Param("id") String id);

   
    
}