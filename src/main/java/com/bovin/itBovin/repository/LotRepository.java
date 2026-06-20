package com.bovin.itBovin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.LotModel;

@Repository
public interface LotRepository extends JpaRepository<LotModel, Integer> {

    // SELECT * FROM lot WHERE id_race = id_race
    @Query("SELECT l FROM LotModel l WHERE l.race.id = :idRace")
    List<LotModel> findAllByRace(@Param("idRace") Long idRace);
}