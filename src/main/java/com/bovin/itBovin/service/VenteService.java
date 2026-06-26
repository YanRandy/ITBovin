package com.bovin.itBovin.service;

import com.bovin.itBovin.err.ClientNotFoundErr;
import com.bovin.itBovin.model.ClientModel;
import com.bovin.itBovin.model.BovinVenteDetailModel;
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

    /**
     * Enregistre une vente complète en s'appuyant sur le modèle BovinVenteDetailModel
     */
    @Transactional
    public void enregistrerVente(Integer clientId, String description, Timestamp dateSaisie, List<BovinVenteDetailModel> listeBovins) throws ClientNotFoundErr {
        
        // 1. Alaina lé client (Vérification de l'existence du client)
        ClientModel client = clientService.getClientById(clientId);

        // Calcul du montant total de la vente
        BigDecimal montantTotal = BigDecimal.ZERO;
        for (BovinVenteDetailModel b : listeBovins) {
            montantTotal = montantTotal.add(b.getPrixVente());
        }

        // 2. Insertion de l'entête de la vente
        Integer idVenteHistorique = venteRepository.insertVenteHistorique(client.getId(), dateSaisie, description, montantTotal);

        // 3. Traitement de chaque numéro de bovin inséré
        for (BovinVenteDetailModel b : listeBovins) {
            Map<String, Object> bovinData = venteRepository.findBovinDataForHistory(b.getIdBovin());

            // Copie et archivage des données du bovin avant sortie
            venteRepository.insertHistoriqueBovin(
                    b.getIdBovin(),
                    bovinData.get("id_race"),
                    bovinData.get("poids_actuel"),
                    bovinData.get("date_naissance"),
                    bovinData.get("date_arrivee")
            );

            // Détacher le bovin de son lot actif (id_lot = NULL)
            venteRepository.detachBovinFromLot(b.getIdBovin());

            // Insertion dans la table de détails d'historique de vente
            venteRepository.insertVenteHistoriqueDetail(idVenteHistorique, b.getIdBovin(), b.getPrixVente());
        }

        // 4. Écritures comptables automatiques dans mouvement_compta
        // Insertion du mouvement principal (Type 2 = Vente)
        Integer idMouvement = venteRepository.insertMouvement(2, idVenteHistorique, dateSaisie);

        // Débit Compte Clients (ID 4) du montant total
        venteRepository.insertMouvementCompta(idMouvement, 4, montantTotal, BigDecimal.ZERO);

        // Crédit Compte Ventes (ID 3) du montant total
        venteRepository.insertMouvementCompta(idMouvement, 3, BigDecimal.ZERO, montantTotal);
    }
}