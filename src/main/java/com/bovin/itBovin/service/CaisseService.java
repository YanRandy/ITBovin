package com.bovin.itBovin.service;

import com.bovin.itBovin.model.CaisseModel;
import com.bovin.itBovin.repository.CaisseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaisseService {
    private final CaisseRepository caisseRepository;

    public CaisseService(CaisseRepository caisseRepository) {
        this.caisseRepository = caisseRepository;
    }

    public List<CaisseModel> findAll() {
        return caisseRepository.findAll();
    }

    public CaisseModel findById(Integer id) {
        return caisseRepository.findById(id).orElse(null);
    }
}