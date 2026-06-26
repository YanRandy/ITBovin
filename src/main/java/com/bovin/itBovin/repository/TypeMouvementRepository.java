package com.bovin.itBovin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.TypeMouvementModel;

@Repository
public interface TypeMouvementRepository extends JpaRepository<TypeMouvementModel,Integer>{
    Optional<TypeMouvementModel> findByLibelle(String libelle);
}
