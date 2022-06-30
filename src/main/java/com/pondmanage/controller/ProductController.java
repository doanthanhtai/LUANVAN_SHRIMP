package com.pondmanage.controller;

import com.pondmanage.AjaxRespone;
import com.pondmanage.dto.ProductDTO;
import com.pondmanage.dto.ShrimpDTO;
import com.pondmanage.dto.ZoneDTO;
import com.pondmanage.model.Account;
import com.pondmanage.model.Product;
import com.pondmanage.model.Zone;
import com.pondmanage.service.AccountService;
import com.pondmanage.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    AccountService accountService;

    @PostMapping("/products/insert-product")
    public ResponseEntity<?> insertProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("insert-fail");
            return ResponseEntity.badRequest().body(ajaxRespone);
        }
        productService.insertProduct(productDTO);
        ajaxRespone.setMsg("insert-success");
        return ResponseEntity.ok(ajaxRespone);
    }

    @PostMapping("/products/get-products")
    public AjaxRespone getProducts() {
        AjaxRespone ajaxRespone = new AjaxRespone();
        Account account = accountService.getCurrentAccount();
        List<ProductDTO> productDTOSs = new ArrayList<>();
        List<Product> products = productService.findAll();
        for (Product product : products) {
            if (product.getAccount().getId().equals(account.getId())) {
                productDTOSs.add(getProductDTOByProduct(product));
            }
        }
        ajaxRespone.setObjects(productDTOSs);
        return ajaxRespone;
    }

    @PostMapping("/products/get-product-info")
    public AjaxRespone getProductInfo(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
        AjaxRespone ajaxRespone = new AjaxRespone();
        if (bindingResult.hasErrors()) {
            ajaxRespone.setMsg("not-found-object");
            return ajaxRespone;
        }
        Product product = productService.getById(productDTO.getId());
        productDTO = getProductDTOByProduct(product);
        ajaxRespone.setObjects(productDTO);
        return ajaxRespone;
    }

//    @PostMapping("/products/get-product-info")
//    public AjaxRespone getProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult bindingResult) {
//        AjaxRespone ajaxRespone = new AjaxRespone();
//        if (bindingResult.hasErrors()) {
//            ajaxRespone.setMsg("not-found-object");
//            return ajaxRespone;
//        }
//        Product product = productService.getById(productDTO.getId());
//        productDTO.setName(product.getName());
//        productDTO.setMeasure(product.getMeasure());
//        productDTO.setEnterPrice(product.getEnterPrice());
//        productDTO.setSupplier(product.getSupplier());
//        ajaxRespone.setObjects(productDTO);
//        return ajaxRespone;
//    }

    private ProductDTO getProductDTOByProduct(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setSupplier(product.getSupplier());
        productDTO.setEnterPrice(product.getEnterPrice());
        productDTO.setMeasure(product.getMeasure());
        return productDTO;
    }
}
