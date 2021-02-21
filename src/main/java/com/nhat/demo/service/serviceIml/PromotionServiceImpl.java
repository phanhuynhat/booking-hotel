package com.nhat.demo.service.serviceIml;

import com.nhat.demo.entity.Promotion;
import com.nhat.demo.repository.PromotionRepository;
import com.nhat.demo.service.PromotionServiceIF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl implements PromotionServiceIF {
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public Promotion getPromotionById(String promotionCode) {
        return promotionRepository.findByPromotionCode(promotionCode);
    }
}
