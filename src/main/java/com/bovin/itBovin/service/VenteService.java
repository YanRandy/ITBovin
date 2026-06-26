package com.bovin.itBovin.service;

import com.bovin.itBovin.dto.PaiementDTO;
import com.bovin.itBovin.err.ClientNotFoundErr;
import com.bovin.itBovin.model.*;
import com.bovin.itBovin.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VenteService {

        @Autowired
        private ClientService clientService;
        @Autowired
        private VenteHistoriqueRepository venteHistoriqueRepository;
        @Autowired
        private VenteHistoriqueDetailRepository venteHistoriqueDetailRepository;
        @Autowired
        private HistoriqueBovinRepository historiqueBovinRepository;
        @Autowired
        private BovinRepository bovinRepository;
        @Autowired
        private VenteDetailPaiementRepository venteDetailRepository;
        @Autowired
        private CaisseRepository caisseRepository;
        @Autowired
        private ComptaService comptaService;

        @Transactional
        public void enregistrerVente(Integer clientId, String description,
                        LocalDateTime dateSaisie,
                        List<BovinVenteDetailModel> listeBovins,
                        List<PaiementDTO> paiements) throws ClientNotFoundErr {

                // 1. Vérification du client
                ClientModel client = clientService.getClientById(clientId);

                // 2. Calcul du montant total
                BigDecimal montantTotal = listeBovins.stream()
                                .map(BovinVenteDetailModel::getPrixVente)
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                // 3. Création de l'entête de vente
                VenteHistoriqueModel vente = creerEnteteVente(client, description, dateSaisie, montantTotal);

                // 4. Traitement des bovins vendus
                traiterBovinsVendus(listeBovins, vente);

                // 5. Enregistrement des paiements
                enregistrerPaiements(paiements, vente);

                // 6. Écritures comptables (délégation)
                comptaService.enregistrerVenteCompta(vente.getId(), montantTotal, dateSaisie);
        }

        // ------------------------------------------------------------------------
        // Méthodes privées
        // ------------------------------------------------------------------------

        private VenteHistoriqueModel creerEnteteVente(ClientModel client, String description,
                        LocalDateTime dateSaisie, BigDecimal montantTotal) {
                VenteHistoriqueModel vente = new VenteHistoriqueModel();
                vente.setClient(client);
                vente.setDateSaisie(dateSaisie);
                vente.setDescription(description);
                vente.setMontantTotalVente(montantTotal);
                return venteHistoriqueRepository.save(vente);
        }

        private void traiterBovinsVendus(List<BovinVenteDetailModel> listeBovins,
                        VenteHistoriqueModel vente) {
                for (BovinVenteDetailModel b : listeBovins) {
                        BovinModel bovin = bovinRepository.findById(b.getIdBovin())
                                        .orElseThrow(() -> new RuntimeException("Bovin non trouvé: " + b.getIdBovin()));

                        archiverBovin(bovin);
                        detacherLot(bovin);
                        ajouterDetailVente(vente, bovin, b.getPrixVente());
                }
        }

        private void archiverBovin(BovinModel bovin) {
                HistoriqueBovinModel historique = new HistoriqueBovinModel();
                historique.setIdBovin(bovin.getId());
                historique.setPoidsAuMomentVente(BigDecimal.valueOf(bovin.getPoidsActuel()));
                historique.setDateSortieVente(LocalDateTime.now());
                historiqueBovinRepository.save(historique);
        }

        private void detacherLot(BovinModel bovin) {
                bovin.setLot(null);
                bovinRepository.save(bovin);
        }

        private void ajouterDetailVente(VenteHistoriqueModel vente, BovinModel bovin, BigDecimal prixVente) {
                VenteHistoriqueDetailModel detail = new VenteHistoriqueDetailModel();
                detail.setVenteHistorique(vente);
                detail.setBovin(bovin);
                detail.setPrixVente(prixVente);
                venteHistoriqueDetailRepository.save(detail);
        }

        /**
         * Enregistre les paiements associés à la vente.
         */
        private void enregistrerPaiements(List<PaiementDTO> paiements, VenteHistoriqueModel vente) {
                if (paiements == null || paiements.isEmpty()) {
                        return;
                }
                for (PaiementDTO paiementDTO : paiements) {
                        // Ignorer les paiements sans montant ou sans caisse
                        if (paiementDTO.getMontant() == null
                                        || paiementDTO.getMontant().compareTo(BigDecimal.ZERO) <= 0) {
                                continue;
                        }
                        if (paiementDTO.getIdCaisse() == null) {
                                throw new RuntimeException("La caisse est obligatoire pour un paiement.");
                        }

                        CaisseModel caisse = caisseRepository.findById(paiementDTO.getIdCaisse())
                                        .orElseThrow(() -> new RuntimeException(
                                                        "Caisse non trouvée: " + paiementDTO.getIdCaisse()));

                        VenteDetailPaiementModel paiement = new VenteDetailPaiementModel();
                        paiement.setVenteHistorique(vente);
                        paiement.setLibelle(
                                        paiementDTO.getLibelle() != null ? paiementDTO.getLibelle() : "Paiement vente");
                        paiement.setMontant(paiementDTO.getMontant());
                        paiement.setDatePaiement(paiementDTO.getDatePaiement() != null ? paiementDTO.getDatePaiement()
                                        : LocalDateTime.now());
                        paiement.setCaisse(caisse);

                        venteDetailRepository.save(paiement);

                        // Optionnel : mettre à jour le solde de la caisse
                        caisse.setSoldeActuelle(caisse.getSoldeActuelle().add(paiementDTO.getMontant()));
                        caisseRepository.save(caisse);
                }
        }
}