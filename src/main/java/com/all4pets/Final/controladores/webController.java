package com.all4pets.Final.controladores;

import com.all4pets.Final.entidades.petEntity;
import com.all4pets.Final.entidades.productEntity;
import com.all4pets.Final.repositorios.productRepository;
import com.all4pets.Final.servicios.petService;
import com.all4pets.Final.servicios.productService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class webController {

    @Autowired
    petService petService;
    @Autowired
    productService productService;
    @Autowired
    productRepository productRepo;

    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    @GetMapping("login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "Email or password invalid");
        }
        return "login.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @GetMapping("/home")
    public String home() {
        return "indexLogged.html";
    }

    @GetMapping("shop")
    public String shop() {

        return "shop.html";
    }

    @GetMapping("cart")
    public String cart(HttpSession session, ModelMap model) {
        List<productEntity> cartProducts = (List<productEntity>) session.getAttribute("cart");
        model.addAttribute("cartProducts", cartProducts);
        return "cart.html";
    }
    
    @GetMapping("delete")
    public String deleteObject(@RequestParam String id, HttpSession session) {
        List<productEntity> productList = (List<productEntity>) session.getAttribute("cart");
         productEntity product1 = productRepo.findProductById(id);
         productList.remove(product1);
         session.setAttribute("cart", productList);
      
       return "redirect:/cart" ;
    }
     @GetMapping("buy")
    public String buy(ModelMap model) {
        
       model.put("success", "Thanks for your purchase");
      
       return "buy.html" ;
    }

    @PostMapping("addCart")
    public String addCart(@RequestParam String productId, ModelMap model, HttpSession session) {
        productEntity productEntity = productRepo.findProductById(productId);

        if ((List<productEntity>) session.getAttribute("cart") == null) {
            List<productEntity> productEntities = new ArrayList();
            productEntities.add(productEntity);
            session.setAttribute("cart", productEntities);
        } else {
            List<productEntity> productEntities = (List<productEntity>) session.getAttribute("cart");
            productEntities.add(productEntity);
            session.setAttribute("cart", productEntities);
        }
        
        return "shop.html";
    }

    @GetMapping("adoptions")
    public String adoptions(Model model) {
        List<petEntity> listPetEntity = petService.showPets();
        model.addAttribute("pet", listPetEntity);

        return "adoptions.html";
    }

}
