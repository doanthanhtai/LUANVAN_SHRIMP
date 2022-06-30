package com.pondmanage.service.imp;

import com.pondmanage.dto.ProductDTO;
import com.pondmanage.model.Product;
import com.pondmanage.repository.ProductRepository;
import com.pondmanage.service.AccountService;
import com.pondmanage.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AccountService accountService;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void insertProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setSupplier(productDTO.getSupplier());
        product.setEnterPrice(productDTO.getEnterPrice());
        product.setMeasure(productDTO.getMeasure());
        product.setAccount(accountService.getCurrentAccount());
        productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.getById(id);
    }
}
