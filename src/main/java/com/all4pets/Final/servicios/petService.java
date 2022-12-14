package com.all4pets.Final.servicios;

import com.all4pets.Final.entidades.imgEntity;
import com.all4pets.Final.entidades.petEntity;
import com.all4pets.Final.enumeraciones.state;
import com.all4pets.Final.excepciones.ownException;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.all4pets.Final.repositorios.petRepository;
import java.util.List;

@Service
public class petService {
    @Autowired
    private userService userService;

    @Autowired
    private petRepository petRepo;

    @Autowired
    private imgService imgService;

    @Transactional
    public petEntity register(String id, String type, String observation, state state, MultipartFile file) throws ownException {
        
        validate(type, observation, state);
        
        petEntity petEntity = new petEntity();
        
        petEntity.setType(type);
        petEntity.setObservations(observation);
        petEntity.setState(state);
        petEntity.setAlta(true);
        petEntity.setUserId(id);
        
        imgEntity imgEntity = imgService.multiPartToEntity(file);
        petEntity.setImgEntity(imgEntity);
        return petRepo.save(petEntity);



    }

    public void validate(String type, String observation, state state) throws ownException {
        
        if (type == null || type.trim().isEmpty()) {
            throw new ownException("Please, indicate pet type");
        }

        if (observation == null || type.trim().isEmpty()) {
            throw new ownException("Please, indicate observations for your pet");
        }
        if (state == null || state.toString().trim().isEmpty()) {
            throw new ownException("This field can't be empty. Please, choose an option");
        }
       
    }

    public void disablePet(String id) throws ownException {

        Optional<petEntity> response = petRepo.findById(id);
        if (response.isPresent()) {
            petEntity petEntity = response.get();
            petEntity.setAlta(Boolean.FALSE);
            petRepo.save(petEntity);
        } else {
            throw new ownException("Pet not found");
        }
    }

    public void enablePet(String id) throws ownException {

        Optional<petEntity> response = petRepo.findById(id);
        if (response.isPresent()) {
            petEntity petEntity = response.get();
            petEntity.setAlta(Boolean.TRUE);
            petRepo.save(petEntity);
        } else {
            throw new ownException("Pet not found");
        }
    }
      public List<petEntity> showPets(){
       
        return (List<petEntity>) petRepo.findAll();
    }
public petEntity findById(String id) throws ownException {

        Optional<petEntity> response = petRepo.findById(id);
        if (response.isPresent()) {
            petEntity petEntity = response.get();
         
        return petEntity;
        } else {
            
        }
            throw new ownException("Pet not found");
        }
}