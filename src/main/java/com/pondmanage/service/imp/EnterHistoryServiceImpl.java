package com.pondmanage.service.imp;

import com.pondmanage.dto.EnterHistoryDTO;
import com.pondmanage.model.EnterHistory;
import com.pondmanage.model.ProductQuantity;
import com.pondmanage.repository.EnterHistoryRepository;
import com.pondmanage.service.EnterHistoryService;
import com.pondmanage.service.ProductQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class EnterHistoryServiceImpl implements EnterHistoryService {

    @Autowired
    EnterHistoryRepository enterHistoryRepository;

    @Autowired
    ProductQuantityService productQuantityService;

    @Override
    public void insert(EnterHistoryDTO enterHistoryDTO) {

        EnterHistory enterHistory = new EnterHistory();
        enterHistory.setIsDeleted(false);
        enterHistory.setCreatedTime(enterHistoryDTO.getCreatedTime());
        enterHistory.setUpdatedTime(enterHistoryDTO.getUpdatedTime());
        enterHistory.setQuantity(enterHistoryDTO.getQuantity());
        enterHistory.setProductQuantity(productQuantityService.insert(enterHistoryDTO));
        enterHistoryRepository.save(enterHistory);
    }

    @Override
    public List<EnterHistory> findByWareHouse(Long wareHouseId) {
        return enterHistoryRepository.findByWareHouse(wareHouseId);
    }

    @Override
    public EnterHistory getById(Long id) {
        return enterHistoryRepository.getById(id);
    }

    @Override
    public void update(EnterHistoryDTO enterHistoryDTO) {
        EnterHistory enterHistory = enterHistoryRepository.getById(enterHistoryDTO.getId());

        ProductQuantity productQuantity = productQuantityService.getById(enterHistory.getProductQuantity().getId());
        productQuantity.setQuantity(productQuantity.getQuantity() - enterHistory.getQuantity() + enterHistoryDTO.getQuantity());

        enterHistory.setProductQuantity(productQuantity);
        enterHistory.setQuantity(enterHistoryDTO.getQuantity());
        enterHistory.setUpdatedTime(enterHistoryDTO.getUpdatedTime());
        enterHistoryRepository.save(enterHistory);
    }

    @Override
    public void delete(EnterHistoryDTO enterHistoryDTO) {
        EnterHistory enterHistory = enterHistoryRepository.getById(enterHistoryDTO.getId());

        ProductQuantity productQuantity = enterHistory.getProductQuantity();
        float newQuantity = productQuantity.getQuantity() - enterHistory.getQuantity();
        if (newQuantity < 0) newQuantity = 0f;
        productQuantity.setQuantity(newQuantity);
        productQuantityService.update(productQuantity);

        enterHistory.setIsDeleted(true);
        enterHistory.setUpdatedTime(enterHistoryDTO.getUpdatedTime());
        enterHistoryRepository.save(enterHistory);
    }
}
