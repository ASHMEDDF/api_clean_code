package com.demo.api.clean.code.repository;

import com.demo.api.clean.code.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameProduct (String nameProduct);
}
