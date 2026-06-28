package com.bovin.itBovin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bovin.itBovin.model.FournisseurModel;
import com.bovin.itBovin.repository.FournisseurRepository;

@Service
public class FournisseurService {
    
    @Autowired
    private FournisseurRepository fournisseurRepository;

    public List<FournisseurModel> findAll() {
        return fournisseurRepository.findAll();
    }

    public Optional<FournisseurModel> findById(Integer idFournisseur) {
        return this.fournisseurRepository.findById(idFournisseur); 
    }

}
