package com.bovin.itBovin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bovin.itBovin.model.RaceModel;
import com.bovin.itBovin.repository.RaceRepository;

@Service
public class RaceService {
    @Autowired
    private RaceRepository raceRepository;

    // utilise la fonction findAll() de JPA et retourne la valeur obtenue
    public List<RaceModel> findAll() {
        return raceRepository.findAll();
    }

    public int getRaceIdByLotId(int lotId) {
        return raceRepository.findRaceByLotId(lotId).getId();
    }

    public RaceModel findById(Integer id) {
        return raceRepository.findById(id).orElse(null);
    }
}