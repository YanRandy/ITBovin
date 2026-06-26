package com.bovin.itBovin.repository;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MouvementRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public int insertMouvement(Integer idTypeMouvement, Integer idAchat, LocalDate dateMouvement) {
        String sql = "INSERT INTO mouvement (id_type_mouvement, id_achat, date_mouvement) VALUES (?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Integer.class, idTypeMouvement, idAchat, dateMouvement);
    }
    
    public void insertMouvementDetail(Integer idMouvement, Integer idCompteCompta, Double debit, Double credit) {
        String sql = "INSERT INTO mouvement_detail (id_mouvement, id_compte_compta, debit, credit) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, idMouvement, idCompteCompta, debit, credit);
    }
}