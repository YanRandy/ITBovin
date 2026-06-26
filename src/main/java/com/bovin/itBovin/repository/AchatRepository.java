package com.bovin.itBovin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bovin.itBovin.model.AchatModel;

public interface AchatRepository extends JpaRepository<AchatModel ,Integer> {
    
}
