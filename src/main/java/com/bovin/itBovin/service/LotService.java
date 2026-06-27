package com.bovin.itBovin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bovin.itBovin.model.LotModel;
import com.bovin.itBovin.repository.LotRepository;

@Service
public class LotService {
    @Autowired
    private LotRepository lotRepository;

    // function to findAll lot
    public List<LotModel> findAll() {
        return lotRepository.findAll();
    }

    // function to save lot
    public LotModel save(LotModel lot) {
        return lotRepository.save(lot);
    }
}
