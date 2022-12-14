package com.all4pets.Final.repositorios;

import com.all4pets.Final.entidades.userEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends JpaRepository<userEntity, String> {

    @Query("SELECT a FROM userEntity a WHERE a.email = :email")
    public userEntity findByEmail(@Param("email") String email);

}