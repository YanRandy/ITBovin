package com.bovin.itBovin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bovin.itBovin.model.MouvementModel;

public interface MouvementRepository extends JpaRepository<MouvementModel,Integer> {
    
}
