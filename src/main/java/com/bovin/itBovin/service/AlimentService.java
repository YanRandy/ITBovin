package com.bovin.itBovin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bovin.itBovin.model.AlimentModel;
import com.bovin.itBovin.repository.AchatAlimentDetailRepository;
import com.bovin.itBovin.repository.AlimentRepository;

public class AlimentService {

    @Autowired
    private AlimentRepository alimentRepository;

    @Autowired 
    private  AchatAlimentDetailRepository achatAlimentDetailRepository;

    public List<AlimentModel> findAll(){
        List<AlimentModel> lsAliment = alimentRepository.findAll();
        return  lsAliment;
    }
    
}
