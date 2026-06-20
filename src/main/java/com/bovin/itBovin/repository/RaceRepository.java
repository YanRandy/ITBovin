package com.bovin.itBovin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.RaceModel;

@Repository
public interface RaceRepository extends JpaRepository<RaceModel, Integer> {
    
}