package com.pondmanage.repository;

import com.pondmanage.model.Shrimp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShrimpRepository extends JpaRepository<Shrimp,Long> {
}
