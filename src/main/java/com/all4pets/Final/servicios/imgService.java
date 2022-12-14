package com.all4pets.Final.servicios;

import com.all4pets.Final.entidades.imgEntity;
import com.all4pets.Final.excepciones.ownException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.all4pets.Final.repositorios.imgRepository;

@Service
public class imgService {
    
    @Autowired
    private imgRepository imagenRepo;
    
    @Transactional
    public imgEntity multiPartToEntity(MultipartFile file) throws ownException {
        
        if(file != null) {
            imgEntity imgEntity = new imgEntity();
            imgEntity.setMime(file.getContentType());
            imgEntity.setImageName(file.getName());
            try {
                imgEntity.setContent(file.getBytes());
            } catch (IOException ex) {
                Logger.getLogger(imgService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return imagenRepo.save(imgEntity);
        } else {
            throw new ownException("No se puede cargar la foto");
        }
        
    }
    
// METODO PARA CAMBIAR 'SOLO' LA FOTO DE PERFIL DEL USUARIO O MASCOTA (TO DO)
    
//    public imgEntity actualizar(String idImagen, MultipartFile archivo) throws ownException {
//        
//        if(archivo != null) {
//            imgEntity imagen = new imgEntity();
//            if(idImagen != null) {
//                Optional<imgEntity> respuesta = imagenRepo.findById(idImagen);
//                if(respuesta.isPresent()) {
//                    imagen = respuesta.get();
//                }
//            }
//            imagen.setMime(archivo.getContentType());
//            imagen.setNombre(archivo.getName());
//            try {
//                imagen.setContenido(archivo.getBytes());
//            } catch (IOException ex) {
//                Logger.getLogger(imgService.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return imagenRepo.save(imagen);
//        } else {
//            throw new ownException("No se puede cargar la foto");
//        }
//        
//    }
}