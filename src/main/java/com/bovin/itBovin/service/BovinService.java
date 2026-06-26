package com.bovin.itBovin.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bovin.itBovin.dto.BovinPayloadDto;
import com.bovin.itBovin.err.BovinNotFoundErr;
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

    public List<BovinModel> getAllBovins() {
        return bovinRepository.findAll();
    }

    public void saveBovinsInLot(List<BovinPayloadDto> bovins) {
        for (BovinPayloadDto bovinPayloadDto : bovins) {
            for (int i = 0; i < bovinPayloadDto.quantite(); i++) {
                BovinModel bovinModel = new BovinModel();
                LotModel lotModel = lotRepository.getReferenceById(bovinPayloadDto.idLot());
                bovinModel.setLot(lotModel);
                bovinModel.setRace(raceRepository.getReferenceById(bovinPayloadDto.idRace()));
                bovinModel.setPoidsInitial(bovinPayloadDto.poidsInitial());
                bovinModel.setPoidsActuel(bovinPayloadDto.poidsActuelle());
                bovinModel.setDateArrivee(Date.valueOf(bovinPayloadDto.dateArrivee()));
                bovinModel.setDateNaissance(Date.valueOf(bovinPayloadDto.dateNaissance()));
                
                bovinRepository.save(bovinModel);
            }
        }

        
    }

        public BovinModel findByLotId(Integer lotId) throws BovinNotFoundErr {
        Optional<BovinModel> maybeBovin= bovinRepository.findById(lotId); // besoin d'implémenter la méthode dans le repository
         if (maybeBovin.isPresent()) {
            return maybeBovin.get();
        } else {
            throw new BovinNotFoundErr(lotId);
        }

        
    }

    public List<BovinModel> getBovinsByLot(Integer lotId) {
    return bovinRepository.findByLotId(lotId);
}
}