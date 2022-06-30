package com.pondmanage.repository;

import com.pondmanage.model.ProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductQuantityRepository extends JpaRepository<ProductQuantity, Long> {
    @Query("SELECT u FROM ProductQuantity u WHERE u.wareHouse.id = ?1 and u.product.id = ?2")
    ProductQuantity findByProductAndWareHouse(Long wareHouseId, Long productId);

    @Query("SELECT SUM(u.quantity) as quantity FROM ProductQuantity u WHERE u.wareHouse.id = ?1")
    Float totalByWareHouse(Long wareHouseId);

    @Query("SELECT u FROM ProductQuantity u WHERE u.wareHouse.id = ?1")
    List<ProductQuantity> getByWareHouse(Long wareHouseId);
}
