package com.bovin.itBovin.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.MouvementModel;

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
    
    public MouvementModel findMouvementAchat(Long idAchat) {
        String sql = "SELECT * FROM mouvement WHERE id_achat = ?";
        List<MouvementModel> result = jdbcTemplate.query(sql, 
            new BeanPropertyRowMapper<>(MouvementModel.class), idAchat);
        return result.isEmpty() ? null : result.get(0);
    }
}