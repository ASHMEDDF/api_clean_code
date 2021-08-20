package com.demo.api.clean.code.controller;

import com.demo.api.clean.code.useCase.ProductsSum;
import com.demo.api.clean.code.entities.Product;
import com.demo.api.clean.code.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductsSum productsSum;

    @GetMapping("/list")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/consult/{nameProduct}")
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable("nameProduct") String nameProduct){
        List<Product> products = productRepository.findByNameProduct(nameProduct);
        if (products.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/value/{firstIdProduct}/{secondIdProduct}")
    public Double sumProducts(@PathVariable("firstIdProduct") Integer firstIdProduct,
                              @PathVariable("secondIdProduct") Integer secondIdProduct){
        Optional<Product> firstProduct = productRepository.findById(firstIdProduct);
        Optional<Product> secondProduct = productRepository.findById(secondIdProduct);
        if (!firstProduct.isPresent() || !secondProduct.isPresent()){
            return null;
        }
        return productsSum.summa(firstProduct.get().getValueProduct(), secondProduct.get().getValueProduct());
    }

    @PostMapping("/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        productRepository.save(product);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/delete/{idProduct}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer idProduct){
        productRepository.deleteById(idProduct);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product productInput){
        Optional<Product> product = productRepository.findById(productInput.getIdProduct());
        if (!product.isPresent()){
            return ResponseEntity.notFound().build();
        }
        Product productUpdated = product.get();
        productUpdated.setNameProduct(productInput.getNameProduct());
        productUpdated.setValueProduct(productInput.getValueProduct());
        productRepository.save(productUpdated);
        return ResponseEntity.ok(productInput);
    }

}
