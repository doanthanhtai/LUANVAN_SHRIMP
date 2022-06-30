package com.pondmanage.service;

import com.pondmanage.dto.ProductDTO;
import com.pondmanage.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    void insertProduct(ProductDTO productDTO);
    Product getById(Long id);

}
