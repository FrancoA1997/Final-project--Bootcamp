package com.all4pets.Final.servicios;

import com.all4pets.Final.entidades.imgEntity;
import com.all4pets.Final.entidades.userEntity;
import com.all4pets.Final.enumeraciones.Rol;
import com.all4pets.Final.excepciones.ownException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import com.all4pets.Final.repositorios.userRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class userService implements UserDetailsService {

    @Autowired
    private userRepository userRepo;

    @Autowired
    private imgService imgService;

    @Transactional(propagation = Propagation.NESTED)
    public userEntity register(String name, String email, String psw) throws ownException {

        validate(name, email, psw);

        userEntity userEntity = new userEntity();

        userEntity.setUserName(name);
        userEntity.setEmail(email);
        userEntity.setAlta(Boolean.TRUE);
        userEntity.setRol(Rol.USER);

        String encryptedPsw = new BCryptPasswordEncoder().encode(psw);
        userEntity.setPsw(encryptedPsw);

        return userRepo.save(userEntity);
    }

    @Transactional(propagation = Propagation.NESTED)
    public void modifyUser(String id,MultipartFile file, String name, String phone, String address) throws ownException {

        validateModifications(name, phone, address);

        Optional<userEntity> respuesta = userRepo.findById(id);
        if (respuesta.isPresent()) {
            userEntity userEntity = respuesta.get();

            userEntity.setUserName(name);
            userEntity.setPhone(phone);
            userEntity.setAddress(address);
            userEntity.setImgEntity(imgService.multiPartToEntity(file));
            userRepo.save(userEntity);
        } else {
            throw new ownException("User not found");
        }

    }

    @Transactional(propagation = Propagation.NESTED)
    public void updateImage(String id, MultipartFile file) throws ownException {

        Optional<userEntity> response = userRepo.findById(id);

        if (response.isPresent()) {
            userEntity userEntity = response.get();

            imgEntity imgEntity = imgService.multiPartToEntity(file);
            userEntity.setImgEntity(imgEntity);

            userRepo.save(userEntity);
        }
    }

    public void disableUser(String id) throws ownException {

        Optional<userEntity> respuesta = userRepo.findById(id);
        if (respuesta.isPresent()) {
            userEntity userEntity = respuesta.get();
            userEntity.setAlta(Boolean.FALSE);
            userRepo.save(userEntity);
        } else {
            throw new ownException("User not found");
        }

    }

    public void enable(String id) throws ownException {

        Optional<userEntity> response = userRepo.findById(id);
        if (response.isPresent()) {
            userEntity userEntity = response.get();
            userEntity.setAlta(Boolean.TRUE);
            userRepo.save(userEntity);
        } else {
            throw new ownException("User not found");
        }

    }

    private void validate(String name, String email, String psw) throws ownException {

        if (name == null || name.trim().isEmpty()) {
            throw new ownException("Name field cant be empty");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new ownException("Email field cant be empty");
        }

        if (psw == null || psw.trim().isEmpty()) {
            throw new ownException("Password field cant be empty");
        }

    }

    public void validateModifications(String phone, String name, String address) throws ownException {

        if (name == null || name.trim().isEmpty()) {
            throw new ownException("Name field cant be empty");
        }
        if (phone == null || phone.trim().isEmpty()) {
            throw new ownException("Phone number field cant be empty");
        }

        if (address == null || address.trim().isEmpty()) {
            throw new ownException("Address field cant be empty");
        }

    }

    @Transactional(readOnly = true)
    public List<userEntity> showAll() {
        return userRepo.findAll();
    }

    @Transactional(propagation = Propagation.NESTED)
    public void deleteById(String id) {
        Optional<userEntity> response = userRepo.findById(id);

        if (response.isPresent()) {
            userRepo.delete(response.get());
        }

    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        userEntity userEntity = userRepo.findByEmail(mail);
        if (userEntity != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            // Concateno la informacion del STRING del ENUM del rol del userEntity
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_" + userEntity.getRol());
            permisos.add(p1);

            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", userEntity);

            User user = new User(userEntity.getEmail(), userEntity.getPsw(), permisos);
            return user;

        } else {
            return null;
        }

    }
  public userEntity findById(String id) throws ownException {

        Optional<userEntity> response = userRepo.findById(id);
        if (response.isPresent()) {
            userEntity userEntity = response.get();
         
        return userEntity;
        } else {
            
        }
            throw new ownException("User not found");
        }
}
