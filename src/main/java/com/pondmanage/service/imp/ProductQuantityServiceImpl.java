package com.pondmanage.service.imp;

import com.pondmanage.dto.EnterHistoryDTO;
import com.pondmanage.model.ProductQuantity;
import com.pondmanage.repository.ProductQuantityRepository;
import com.pondmanage.repository.WareHouseRepository;
import com.pondmanage.service.ProductQuantityService;
import com.pondmanage.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQuantityServiceImpl implements ProductQuantityService {

    @Autowired
    ProductQuantityRepository productQuantityRepository;

    @Autowired
    ProductService productService;

    @Autowired
    WareHouseRepository wareHouseRepository;

    @Override
    public ProductQuantity insert(EnterHistoryDTO enterHistoryDTO) {
        Long wareHouseId = enterHistoryDTO.getProductQuantityDTO().getWareHouseId();
        Long productId = enterHistoryDTO.getProductQuantityDTO().getProductId();
        ProductQuantity productQuantity = productQuantityRepository.findByProductAndWareHouse(wareHouseId, productId);
        if (productQuantity == null) {
            productQuantity = new ProductQuantity();
            productQuantity.setProduct(productService.getById(productId));
            productQuantity.setWareHouse(wareHouseRepository.getById(wareHouseId));
            productQuantity.setQuantity(enterHistoryDTO.getQuantity());
            productQuantityRepository.save(productQuantity);
            return productQuantity;
        }
        productQuantity.setQuantity(productQuantity.getQuantity() + enterHistoryDTO.getQuantity());
        return productQuantity;
    }

    @Override
    public Float totalByWareHouse(Long wareHouseId) {
        return productQuantityRepository.totalByWareHouse(wareHouseId);
    }

    @Override
    public List<ProductQuantity> getByWareHouse(Long wareHouseId) {
        return productQuantityRepository.getByWareHouse(wareHouseId);
    }

    @Override
    public void update(ProductQuantity productQuantity) {
        productQuantityRepository.save(productQuantity);
    }

    @Override
    public ProductQuantity getById(Long id){
        return productQuantityRepository.getById(id);
    }

}
