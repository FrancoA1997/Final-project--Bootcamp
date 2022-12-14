package com.all4pets.Final.controladores;

import com.all4pets.Final.entidades.petEntity;
import com.all4pets.Final.entidades.userEntity;
import com.all4pets.Final.excepciones.ownException;
import com.all4pets.Final.servicios.petService;
import com.all4pets.Final.servicios.userService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image")
public class imgController {

    @Autowired
    private userService userService;
    @Autowired
    private petService petService;

    @GetMapping("/user")
    public ResponseEntity<byte[]> userImage(String id) throws ownException {
        try {
            userEntity userEntity = userService.findById(id);
            if (userEntity.getImgEntity() == null) {
                throw new ownException("This user has no image");
            }
            byte[] imagen = userEntity.getImgEntity().getContent();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
        } catch (ownException e) {
            Logger.getLogger(imgController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/pet")
    public ResponseEntity<byte[]> petImage(String id) throws ownException {
        try {
            petEntity petEntity = petService.findById(id);
            if (petEntity.getImgEntity() == null) {
                throw new ownException("This pet has no image");
            }
            byte[] imagen = petEntity.getImgEntity().getContent();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagen, headers, HttpStatus.OK);
        } catch (ownException e) {
            Logger.getLogger(imgController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
