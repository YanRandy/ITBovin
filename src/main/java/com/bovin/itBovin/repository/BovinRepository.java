package com.bovin.itBovin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.BovinModel;

@Repository
public interface BovinRepository extends JpaRepository<BovinModel, Integer> {
     @Modifying
    @Query("UPDATE BovinModel b SET b.lot = NULL WHERE b.id = :id")
    void detachLot(@Param("id") Integer id);
    List<BovinModel> findByLotId(Integer lotId);
}
