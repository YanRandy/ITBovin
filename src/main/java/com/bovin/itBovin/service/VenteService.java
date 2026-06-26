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

    @Transactional
    public void enregistrerVente(Integer clientId, String description, Timestamp dateSaisie, List<BovinVenteDetailModel> listeBovins) throws ClientNotFoundErr {
        
        // 1. Alaina lé client
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

            // Archivage dans historique_bovin
            venteRepository.insertHistoriqueBovin(
                    b.getIdBovin(),
                    bovinData.get("id_race"),
                    bovinData.get("poids_actuel"),
                    bovinData.get("date_naissance"),
                    bovinData.get("date_arrivee")
            );

            // Retrait du lot actif
            venteRepository.detachBovinFromLot(b.getIdBovin());

            // Insertion du détail
            venteRepository.insertVenteHistoriqueDetail(idVenteHistorique, b.getIdBovin(), b.getPrixVente());
        }

        // =========================================================================
        // 4. Écritures Comptables selon la nouvelle structure d'équipe
        // =========================================================================
        
        // Écriture 1 : DEBIT (id_type_mouvement = 1) sur le compte Clients (ID 4)
        Integer idMouvementDebit = venteRepository.insertMouvement(1, idVenteHistorique, dateSaisie);
        venteRepository.insertMouvementCompta(idMouvementDebit, 4, montantTotal, BigDecimal.ZERO);

        // Écriture 2 : CREDIT (id_type_mouvement = 2) sur le compte Ventes (ID 3)
        Integer idMouvementCredit = venteRepository.insertMouvement(2, idVenteHistorique, dateSaisie);
        venteRepository.insertMouvementCompta(idMouvementCredit, 3, BigDecimal.ZERO, montantTotal);
    }
}