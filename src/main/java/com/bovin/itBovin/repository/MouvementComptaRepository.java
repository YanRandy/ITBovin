package com.bovin.itBovin.repository;

import com.bovin.itBovin.model.MouvementComptaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MouvementComptaRepository extends JpaRepository<MouvementComptaModel, Integer> {
}