package com.pondmanage.repository;

import com.pondmanage.model.WareHouse;
import com.pondmanage.model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, Long> {
}
