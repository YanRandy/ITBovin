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

    public RaceService(RaceRepository raceRepository) {
        this.raceRepository = raceRepository;
    }

    public List<RaceModel> getAllRaces() {
        return raceRepository.findAll();
    }

    public RaceModel createRace(RaceModel race) {
        return raceRepository.save(race);
    }

    public int getRaceIdByLotId(int lotId) {
        return raceRepository.findRaceIdByLotId(lotId);
    }

    public RaceModel findById(Integer id_race) {
        return raceRepository.findById(id_race).orElse(null);
    }

    public void deleteById(Integer id_race) {
        raceRepository.deleteById(id_race);
    }
}
