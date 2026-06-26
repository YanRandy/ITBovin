package com.bovin.itBovin.repository;

import com.bovin.itBovin.model.VenteDetailPaiementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenteDetailPaiementRepository extends JpaRepository<VenteDetailPaiementModel, Integer> {
}