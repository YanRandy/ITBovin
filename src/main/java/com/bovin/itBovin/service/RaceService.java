package com.bovin.itBovin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bovin.itBovin.repository.RaceRepository;

@Service
public class RaceService {
    @Autowired
    private RaceRepository raceRepository;

    public int getRaceIdByLotId(int lotId) {
        return raceRepository.findRaceIdByLotId(lotId);
    }
}
