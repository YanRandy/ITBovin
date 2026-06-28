package com.bovin.itBovin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.AchatAlimentDetail;

@Repository
public interface  AchatAlimentDetailRepository extends JpaRepository<AchatAlimentDetail, Integer>{
    
}
