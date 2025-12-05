package com.onlineShop.springBoot.example.service;

import com.onlineShop.springBoot.example.entity.Category;
import com.onlineShop.springBoot.example.entity.Product;
import com.onlineShop.springBoot.example.repsitory.CategoryRepository;
import com.onlineShop.springBoot.example.repsitory.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product addProduct(Product product, Long categoryID) {
        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new RuntimeException("category not found"));
        product.setCategory(category);
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found"));
    }

    public void  DeleteProduct(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found"));
        productRepository.delete(product);
    }

    public Product updateProduct(Product UpdateProduct, Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found"));
        product.setName(UpdateProduct.getName());
        product.setBrand(UpdateProduct.getBrand());
        product.setModel(UpdateProduct.getModel());
        product.setMade_in(UpdateProduct.getMade_in());
        product.setYear_of_manufacture(UpdateProduct.getYear_of_manufacture());
        product.setDesign(UpdateProduct.getDesign());
        product.setPrice(UpdateProduct.getPrice());
        product.setCategory(UpdateProduct.getCategory());
        return productRepository.save(product);
    }

}
