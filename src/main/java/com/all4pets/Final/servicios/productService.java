package com.all4pets.Final.servicios;

import com.all4pets.Final.entidades.productEntity;
import com.all4pets.Final.enumeraciones.type;
import com.all4pets.Final.excepciones.ownException;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.all4pets.Final.repositorios.productRepository;
import java.util.List;

@Service
public class productService {
    
    @Autowired
    private productRepository productRepo;
    
    @Transactional
    public void crearProducto(String Id, type type, Integer price, Boolean stock, String description) throws ownException {
        
        validate(type, price, stock, description);
        
        productEntity p1 = new productEntity();
        
      
        p1.setPrice(price);
        p1.setStock(stock);
        p1.setDescription(description);

        productRepo.save(p1);
        
}
    public void disableStock(String id) throws ownException {

        Optional<productEntity> response = productRepo.findById(id);
        if (response.isPresent()) {
            productEntity productEntity = response.get();
            productEntity.setStock(Boolean.FALSE);
            productRepo.save(productEntity);
        } else {
            throw new ownException("Product not found or not available");
        }
    }
    
    public void deleteProduct(String Id) {
        Optional<productEntity> response = productRepo.findById(Id);

        if (response.isPresent()) {
            productRepo.delete(response.get());
        }
    }
    
    private void validate(type type, Integer price, Boolean stock, String descripcion) throws ownException {

        if (type == null ) {
            throw new ownException("Please, indicate type of product");
        }

        if (price == null ) {
            throw new ownException("Please, indicate price for this product");
        }

        if (stock == null ) {
            throw new ownException("Please, indicate if this product is available");
        }
        
        if (descripcion == null ) {
            throw new ownException("Por favor, add a brief description for this product");
        }

    }
    public List<productEntity> productList(){
       
        return (List<productEntity>) productRepo.findAll();
    }
}
