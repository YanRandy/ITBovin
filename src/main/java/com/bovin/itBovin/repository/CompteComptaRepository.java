package com.bovin.itBovin.repository;

import com.bovin.itBovin.model.CompteComptaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompteComptaRepository extends JpaRepository<CompteComptaModel, Integer> {
}