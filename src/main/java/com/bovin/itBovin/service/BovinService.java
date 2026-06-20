package com.bovin.itBovin.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bovin.itBovin.dto.BovinPayloadDto;
import com.bovin.itBovin.model.BovinModel;
import com.bovin.itBovin.model.LotModel;
import com.bovin.itBovin.repository.BovinRepository;
import com.bovin.itBovin.repository.LotRepository;
import com.bovin.itBovin.repository.RaceRepository;

@Service
public class BovinService {
    @Autowired
    private BovinRepository bovinRepository;
    @Autowired
    private LotRepository lotRepository;
    @Autowired
    private RaceRepository raceRepository;

    // utilise la fonction findAll() de JPA et retourne la valeur obtenue
    public List<BovinModel> findAll() {
        return bovinRepository.findAll();
    }

    public void save(BovinModel bovin) {
        bovinRepository.save(bovin);
    }

    public void saveBovinsInLot(List<BovinPayloadDto> bovins) {
        for (BovinPayloadDto bovinPayloadDto : bovins) {
            for (int i = 0; i < bovinPayloadDto.getQuantite(); i++) {
                BovinModel bovinModel = new BovinModel();
                LotModel lotModel = lotRepository.getReferenceById(bovinPayloadDto.getIdLot());
                bovinModel.setLot(lotModel);
                bovinModel.setRace(raceRepository.getReferenceById(bovinPayloadDto.getIdRace()));
                bovinModel.setPoidsInit(bovinPayloadDto.getPoidsInit());
                bovinModel.setPoidsActuel(bovinPayloadDto.getPoidsActuelle());
                bovinModel.setDateArrivee(Date.valueOf(bovinPayloadDto.getDateArrivee()));
                bovinModel.setDateInit(Date.valueOf(bovinPayloadDto.getDateInit()));
                
                bovinRepository.save(bovinModel);
            }
        }
    }
}