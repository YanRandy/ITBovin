package com.bovin.itBovin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.CompteComptaModel;

@Repository
public interface CompteComptaRepository extends JpaRepository<CompteComptaModel , Integer> {
    Optional<CompteComptaModel> findByNumero(Integer numero);
}
