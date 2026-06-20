package com.bovin.itBovin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.RaceModel;

@Repository
public interface RaceRepository extends JpaRepository<RaceModel, Integer> {
    @Query("SELECT r.id FROM RaceModel r JOIN LotModel l ON r.id = l.race.id WHERE l.id = :lotId")
    public int findRaceIdByLotId(@Param("lotId") int lotId);
}