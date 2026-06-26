package com.bovin.itBovin.repository;

import com.bovin.itBovin.model.MouvementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MouvementRepository extends JpaRepository<MouvementModel, Integer> {
}