package com.bovin.itBovin.service;

import com.bovin.itBovin.model.LotModel;
import com.bovin.itBovin.repository.LotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LotService {
    private final LotRepository lotRepository;

    public LotService(LotRepository lotRepository) {
        this.lotRepository = lotRepository;
    }

    public List<LotModel> findAll() {
        return lotRepository.findAll();
    }

    public LotModel findById(Integer id) {
        return lotRepository.findById(id).orElse(null);
    }
}