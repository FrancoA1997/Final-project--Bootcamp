package com.all4pets.Final.controladores;

import com.all4pets.Final.entidades.userEntity;
import com.all4pets.Final.enumeraciones.state;
import com.all4pets.Final.enumeraciones.userGender;
import com.all4pets.Final.excepciones.ownException;
import com.all4pets.Final.repositorios.userRepository;
import com.all4pets.Final.servicios.petService;
import com.all4pets.Final.servicios.userService;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("user")
public class userController {
    
    @Autowired
    private userService userService;
    @Autowired
    private userRepository userRepo;
    
    @Autowired
    private petService petService;
    
    @GetMapping("/logout")
    public String logout() {
        return "index.html";
    }
    
    @PostMapping("register")
    public String register(ModelMap model, @RequestParam String name, @RequestParam String email, @RequestParam String pswd) throws ownException {
        
        try {
            userService.register(name, email, pswd);
        } catch (ownException e) {
            model.put("error", e.getMessage());
            model.put("name", name);
            model.put("email", email);
            model.put("pswd", pswd);
        }
        
       
         model.put("success", "Register successfuly");
          model.put("success2", "Welcome!");
        return "login.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("profile")
    public String getProfile(@RequestParam String id) {
        return "profile.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("editProfileDisplay")
    public String formDataDisplay(ModelMap modelo) {
        modelo.addAttribute("userGender", userGender.values());
        return "profile.html";
    }
    
//    @PostMapping("modificar")
//    public String modificar(ModelMap modelo, @RequestParam String id, @RequestParam userGender sexo, @RequestParam Integer edad, @RequestParam String telefono, @RequestParam String direccion) throws ownException {
//        
//        try {
//            userService.modificar(id, sexo, edad, telefono, direccion);
//        } catch (ownException e) {
//            modelo.put("error", e.getMessage());
//            modelo.put("sexo", sexo);
//            modelo.put("edad", edad);
//            modelo.put("telefono", telefono);
//            modelo.put("direccion", direccion);
//        }
//        
//        modelo.put("exito", "Perfil actualizado con éxito");
//        return "profile.html";
//    }
    
    @PostMapping("updateImage")
    public String updateImage(ModelMap modelo, @RequestParam String id, MultipartFile file) throws ownException {
        
        try {
            userService.updateImage(id, file);
        } catch (ownException e) {
            modelo.put("error", e.getMessage());
        }
        
        modelo.put("success", "Image updated successfuly");
        return "profile.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("uploadPet")
    public String upload(ModelMap model) {
        model.addAttribute("states", state.values());
        return "uploadPet.html";
    }
    
    @PostMapping("uploadNewPet")
    public String uploadPet(ModelMap model, @RequestParam String id, @RequestParam String type, @RequestParam String observation, @RequestParam state state1, MultipartFile file) throws ownException {
        
        try {
            Optional<userEntity> response = userRepo.findById(id);
            if (response.isPresent()) {
                userEntity user1 = response.get();
                user1.setPetEntity(petService.register(id, type, observation, state1, file));
                userRepo.save(user1);
            }
        } catch (ownException e) {
            model.put("error", e.getMessage());
            return"uploadPet.html";
        }
        model.put("success", "pet upload successfuly");
        return "adoptions.html";
    }
     @GetMapping("profilePetOwner")
    public String profilePetOwner(@RequestParam String id, ModelMap model) {
           Optional<userEntity> response = userRepo.findById(id);
         if (response.isPresent()) {
            userEntity userPetOwner = response.get();
            model.addAttribute("userPetOwner", userPetOwner);
         }
        return "profileUserPetOwner.html";
    }

    @PostMapping("edit")
    public String editData(@RequestParam String id,MultipartFile file, HttpSession session ,String name, ModelMap model, String phone, String address) throws ownException {
           Optional<userEntity> response = userRepo.findById(id);
         if (response.isPresent()) {
            userEntity userEntity = response.get();
            model.addAttribute("userEntity", userEntity);
            userService.modifyUser(id ,file, name, phone, address);
            session.setAttribute("usuariosession", userEntity);
         }
        return "profile.html";
  
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("editProfile")
    public String editProfileData(@RequestParam String id, ModelMap model) throws ownException {
           Optional<userEntity> response = userRepo.findById(id);
         if (response.isPresent()) {
            userEntity userEntity = response.get();
            model.addAttribute("userEntity", userEntity);
        } 
        return "updateProfileData.html";
  

            }  
    
}
