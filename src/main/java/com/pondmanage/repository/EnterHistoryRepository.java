package com.pondmanage.repository;

import com.pondmanage.model.EnterHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnterHistoryRepository extends JpaRepository<EnterHistory, Long> {
    @Query("SELECT e FROM EnterHistory e WHERE e.productQuantity.wareHouse.id = ?1 and e.isDeleted = false")
    List<EnterHistory> findByWareHouse(Long wareHouseId);
}
