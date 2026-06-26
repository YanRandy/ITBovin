package com.bovin.itBovin.repository;

import com.bovin.itBovin.model.TypeMouvementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeMouvementRepository extends JpaRepository<TypeMouvementModel, Integer> {
}