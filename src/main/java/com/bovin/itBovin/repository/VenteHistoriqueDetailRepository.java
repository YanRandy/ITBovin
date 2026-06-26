package com.bovin.itBovin.repository;

import com.bovin.itBovin.model.VenteHistoriqueDetailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenteHistoriqueDetailRepository extends JpaRepository<VenteHistoriqueDetailModel, Integer> {
}