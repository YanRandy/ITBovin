package com.bovin.itBovin.repository;

import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.CaisseModel;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CaisseRepository extends JpaRepository<CaisseModel, Integer>{
    
}
