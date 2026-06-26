package com.bovin.itBovin.repository;

import com.bovin.itBovin.model.HistoriqueBovinModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoriqueBovinRepository extends JpaRepository<HistoriqueBovinModel, Integer> {
}