package com.bovin.itBovin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

@Repository
public class VenteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Integer insertVenteHistorique(Integer clientId, Timestamp dateSaisie, String description, BigDecimal montantTotal) {
        String sql = "INSERT INTO vente_historique (id_client, date_saisie, description, montant_total_vente) " +
                     "VALUES (?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Integer.class, clientId, dateSaisie, description, montantTotal);
    }

    public Map<String, Object> findBovinDataForHistory(Integer idBovin) {
        String sql = "SELECT id_race, poids_actuel, date_naissance, date_arrivee FROM bovin WHERE id = ?";
        return jdbcTemplate.queryForMap(sql, idBovin);
    }

    public void insertHistoriqueBovin(Integer id, Object idRace, Object poids, Object dateNaissance, Object dateArrivee) {
        String sql = "INSERT INTO historique_bovin (id, id_race, poids_au_moment_vente, date_naissance, date_arrivee) " +
                     "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, id, idRace, poids, dateNaissance, dateArrivee);
    }

    public void detachBovinFromLot(Integer idBovin) {
        String sql = "UPDATE bovin SET id_lot = NULL WHERE id = ?";
        jdbcTemplate.update(sql, idBovin);
    }

    public void insertVenteHistoriqueDetail(Integer idVenteHistorique, Integer idBovin, BigDecimal prixVente) {
        String sql = "INSERT INTO vente_historique_detail (id_vente_historique, id_bovin, prix_vente) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, idVenteHistorique, idBovin, prixVente);
    }

    public Integer insertMouvement(Integer idTypeMouvement, Integer idVente, Timestamp dateMouvement) {
        String sql = "INSERT INTO mouvement (id_type_mouvement, id_vente, date_mouvement) VALUES (?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(sql, Integer.class, idTypeMouvement, idVente, dateMouvement);
    }

    public void insertMouvementCompta(Integer idMouvement, Integer idCompteCompta, BigDecimal debit, BigDecimal credit) {
        String sql = "INSERT INTO mouvement_compta (id_mouvement, id_compte_compta, debit, credit) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, idMouvement, idCompteCompta, debit, credit);
    }
}