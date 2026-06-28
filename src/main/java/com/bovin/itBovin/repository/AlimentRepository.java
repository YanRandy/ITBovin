package com.bovin.itBovin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.AlimentModel;

@Repository
public interface  AlimentRepository extends JpaRepository<AlimentModel, Integer> {
    
}
