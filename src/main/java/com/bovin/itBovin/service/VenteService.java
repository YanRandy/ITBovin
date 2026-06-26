package com.bovin.itBovin.service;

import com.bovin.itBovin.err.ClientNotFoundErr;
import com.bovin.itBovin.model.ClientModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class VenteService {

    @Autowired
    private ClientService clientService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * DTO pour gérer l'insertion multiple de bovins saisis par leur numéro
     */
    public static class BovinVenteDetail {
        private Integer idBovin;
        private BigDecimal prixVente;

        public Integer getIdBovin() { return idBovin; }
        public void setIdBovin(Integer idBovin) { this.idBovin = idBovin; }
        public BigDecimal getPrixVente() { return prixVente; }
        public void setPrixVente(BigDecimal prixVente) { this.prixVente = prixVente; }
    }

    /**
     * Enregistre une vente complète avec historique, archivage des bovins et écritures comptables
     */
    @Transactional
    public void enregistrerVente(Integer clientId, String description, Timestamp dateSaisie, List<BovinVenteDetail> listeBovins) throws ClientNotFoundErr {
        
        // 1. Alaina lé client (Vérification de l'existence du client)
        ClientModel client = clientService.getClientById(clientId);

        // Calcul du montant total de la vente
        BigDecimal montantTotal = BigDecimal.ZERO;
        for (BovinVenteDetail b : listeBovins) {
            montantTotal = montantTotal.add(b.getPrixVente());
        }

        // 2. Insertion dans la table vente_historique
        String sqlVente = "INSERT INTO vente_historique (id_client, date_saisie, description, montant_total_vente) " +
                          "VALUES (?, ?, ?, ?) RETURNING id";
        
        Integer idVenteHistorique = jdbcTemplate.queryForObject(sqlVente, Integer.class, 
                client.getId(), dateSaisie, description, montantTotal);

        // 3. Traitement pour chaque numéro de bovin inséré
        for (BovinVenteDetail b : listeBovins) {
            
            // Récupération des informations actuelles du bovin
            String sqlSelectBovin = "SELECT id_race, poids_actuel, date_naissance, date_arrivee FROM bovin WHERE id = ?";
            Map<String, Object> bovinData = jdbcTemplate.queryForMap(sqlSelectBovin, b.getIdBovin());

            // Copie dans historique_bovin
            String sqlInsertHistBovin = "INSERT INTO historique_bovin (id, id_race, poids_au_moment_vente, date_naissance, date_arrivee) " +
                                        "VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlInsertHistBovin,
                    b.getIdBovin(),
                    bovinData.get("id_race"),
                    bovinData.get("poids_actuel"),
                    bovinData.get("date_naissance"),
                    bovinData.get("date_arrivee")
            );

            // Retrait du bovin de son lot actif (id_lot = NULL)
            String sqlUpdateBovin = "UPDATE bovin SET id_lot = NULL WHERE id = ?";
            jdbcTemplate.update(sqlUpdateBovin, b.getIdBovin());

            // Insertion du détail de la vente
            String sqlInsertDetail = "INSERT INTO vente_historique_detail (id_vente_historique, id_bovin, prix_vente) VALUES (?, ?, ?)";
            jdbcTemplate.update(sqlInsertDetail, idVenteHistorique, b.getIdBovin(), b.getPrixVente());
        }

        // 4. Insertion du mouvement comptable (id_type_mouvement = 2 pour Vente)
        String sqlMouvement = "INSERT INTO movimiento (id_type_mouvement, id_vente, date_mouvement) VALUES (?, ?, ?) RETURNING id";
        // Correction dynamique selon le schéma de base : si la table s'appelle "mouvement", ajuster ici
        String tableMvt = "mouvement";
        String sqlMvtFinal = "INSERT INTO " + tableMvt + " (id_type_mouvement, id_vente, date_mouvement) VALUES (?, ?, ?) RETURNING id";
        Integer idMouvement = jdbcTemplate.queryForObject(sqlMvtFinal, Integer.class, 2, idVenteHistorique, dateSaisie);

        // Écritures comptables automatiques dans mouvement_compta :
        // Compte Clients (Débit du montant total de la vente)
        String sqlCompta = "INSERT INTO mouvement_compta (id_mouvement, id_compte_compta, debit, credit) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sqlCompta, idMouvement, 4, montantTotal, BigDecimal.ZERO);

        // Compte Ventes (Crédit du montant total de la vente)
        jdbcTemplate.update(sqlCompta, idMouvement, 3, BigDecimal.ZERO, montantTotal);
    }
}