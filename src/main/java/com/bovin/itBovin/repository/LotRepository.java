package com.bovin.itBovin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.LotModel;

@Repository
public interface LotRepository extends JpaRepository<LotModel, Integer> {
    
}
