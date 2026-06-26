package com.bovin.itBovin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.DetteFournisseurView;

@Repository
public interface DetteFournisseurRepository extends JpaRepository<DetteFournisseurView, Integer> {
    
    @Query(value = "SELECT * FROM v_dette_fournisseur ORDER BY id_achat", nativeQuery = true)
    List<DetteFournisseurView> findAllDettes();
}