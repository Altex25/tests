package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> searchByKeyword(String keyword) {
        return productRepository.searchByKeyword(keyword);
    }

    public List<Product> searchByMaxPrice(double maxPrice) {
        return productRepository.findByMaxPrice(maxPrice);
    }

    public List<Product> browseCategory(String category) {
        return productRepository.findByCategory(category);
    }
}
