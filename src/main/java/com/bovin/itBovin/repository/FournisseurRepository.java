package com.bovin.itBovin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.FournisseurModel;

@Repository
public interface FournisseurRepository extends JpaRepository<FournisseurModel, Integer> {

}
