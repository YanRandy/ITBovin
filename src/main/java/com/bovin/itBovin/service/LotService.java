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

    // appelle la fonction findAllByRace(Long id_race) de LotRepository et retourne la valeur reçue
    public List<LotModel> findAllByRace(Long idRace) {
        return lotRepository.findAllByRace(idRace);
    }
}