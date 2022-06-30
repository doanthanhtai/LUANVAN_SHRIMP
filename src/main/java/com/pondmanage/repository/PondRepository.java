package com.pondmanage.repository;

import com.pondmanage.model.Pond;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PondRepository extends JpaRepository<Pond,Long> {
}
