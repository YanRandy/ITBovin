package com.bovin.itBovin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bovin.itBovin.model.BovinModel;
import com.bovin.itBovin.repository.BovinRepository;

@Service
public class BovinService {
    @Autowired
    private BovinRepository bovinRepository;

    public List<BovinModel> getAllBovins() {
        return bovinRepository.findAll();
    }
}