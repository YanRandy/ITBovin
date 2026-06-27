package com.bovin.itBovin.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bovin.itBovin.model.MouvementDetailModel;

@Repository
public class MouvementDetailRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public List<MouvementDetailModel> findDetailByMouvementAndCompte(Long idMouvement, Long idCompteCompta) {
        String sql = "SELECT * FROM mouvement_detail WHERE id_mouvement = ? AND id_compte_compta = ?";
        return jdbcTemplate.query(sql, 
            new BeanPropertyRowMapper<>(MouvementDetailModel.class), 
            idMouvement, idCompteCompta);
    }
    
    public MouvementDetailModel findDetailByMouvementAndCompteSingle(Long idMouvement, Long idCompteCompta) {
        String sql = "SELECT * FROM mouvement_detail WHERE id_mouvement = ? AND id_compte_compta = ? LIMIT 1";
        List<MouvementDetailModel> result = jdbcTemplate.query(sql, 
            new BeanPropertyRowMapper<>(MouvementDetailModel.class), 
            idMouvement, idCompteCompta);
        return result.isEmpty() ? null : result.get(0);
    }
    
    public void updateDebit(Long id, Double nouveauDebit) {
        String sql = "UPDATE mouvement_detail SET debit = ? WHERE id = ?";
        jdbcTemplate.update(sql, nouveauDebit, id);
    }
    
    public void updateCredit(Long id, Double nouveauCredit) {
        String sql = "UPDATE mouvement_detail SET credit = ? WHERE id = ?";
        jdbcTemplate.update(sql, nouveauCredit, id);
    }
}