package com.pondmanage.service;

import com.pondmanage.dto.EnterHistoryDTO;
import com.pondmanage.model.EnterHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EnterHistoryService {
    void insert(EnterHistoryDTO enterHistoryDTO);

    List<EnterHistory> findByWareHouse(Long wareHouseId);

    EnterHistory getById(Long id);

    void update(EnterHistoryDTO enterHistoryDTO);

    void delete(EnterHistoryDTO enterHistoryDTO);
}
