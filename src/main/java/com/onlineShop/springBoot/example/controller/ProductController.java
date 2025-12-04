package com.onlineShop.springBoot.example.controller;

import com.onlineShop.springBoot.example.entity.*;
import com.onlineShop.springBoot.example.model.ProductModel;
import com.onlineShop.springBoot.example.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductModel request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setModel(request.getModel());
        product.setMade_in(request.getMade_in());
        product.setYear_of_manufacture(request.getYear_of_manufacture());
        product.setDesign(request.getDesign());
        product.setPrice(request.getPrice());

        Product save = productService.addProduct(product, request.getCategoryId());
        return ResponseEntity.ok(save);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        }catch (Exception e){
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getId(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}
