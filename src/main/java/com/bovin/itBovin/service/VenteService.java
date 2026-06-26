package com.bovin.itBovin.service;

import com.bovin.itBovin.err.ClientNotFoundErr;
import com.bovin.itBovin.model.ClientModel;
import com.bovin.itBovin.repository.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private VenteRepository venteRepository;

    public static class BovinVenteDetail {
        private Integer idBovin;
        private BigDecimal prixVente;

        public Integer getIdBovin() { return idBovin; }
        public void setIdBovin(Integer idBovin) { this.idBovin = idBovin; }
        public BigDecimal getPrixVente() { return prixVente; }
        public void setPrixVente(BigDecimal prixVente) { this.prixVente = prixVente; }
    }

    @Transactional
    public void enregistrerVente(Integer clientId, String description, Timestamp dateSaisie, List<BovinVenteDetail> listeBovins) throws ClientNotFoundErr {
        
        // 1. Alaina lé client
        ClientModel client = clientService.getClientById(clientId);

        // Calcul du montant total
        BigDecimal montantTotal = BigDecimal.ZERO;
        for (BovinVenteDetail b : listeBovins) {
            montantTotal = montantTotal.add(b.getPrixVente());
        }

        // 2. Insertion de l'entête de la vente
        Integer idVenteHistorique = venteRepository.insertVenteHistorique(client.getId(), dateSaisie, description, montantTotal);

        // 3. Traitement de chaque numéro de bovin
        for (BovinVenteDetail b : listeBovins) {
            Map<String, Object> bovinData = venteRepository.findBovinDataForHistory(b.getIdBovin());

            // Copie dans l'historique d'archivage des bovins
            venteRepository.insertHistoriqueBovin(
                    b.getIdBovin(),
                    bovinData.get("id_race"),
                    bovinData.get("poids_actuel"),
                    bovinData.get("date_naissance"),
                    bovinData.get("date_arrivee")
            );

            // Enlever le bovin du lot actif
            venteRepository.detachBovinFromLot(b.getIdBovin());

            // Insertion du détail du bovin vendu
            venteRepository.insertVenteHistoriqueDetail(idVenteHistorique, b.getIdBovin(), b.getPrixVente());
        }

        // 4. Comptabilité : Insertion du mouvement (Type 2 = Vente)
        Integer idMouvement = venteRepository.insertMouvement(2, idVenteHistorique, dateSaisie);

        // Débit Compte Clients (ID 4)
        venteRepository.insertMouvementCompta(idMouvement, 4, montantTotal, BigDecimal.ZERO);

        // Crédit Compte Ventes (ID 3)
        venteRepository.insertMouvementCompta(idMouvement, 3, BigDecimal.ZERO, montantTotal);
    }
}