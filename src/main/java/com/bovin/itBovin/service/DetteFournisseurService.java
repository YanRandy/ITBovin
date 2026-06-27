package com.bovin.itBovin.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bovin.itBovin.model.DetteFournisseurView;
import com.bovin.itBovin.model.MouvementDetailModel;
import com.bovin.itBovin.model.MouvementModel;
import com.bovin.itBovin.repository.DetteFournisseurRepository;
import com.bovin.itBovin.repository.MouvementRepository;

@Service
public class DetteFournisseurService {
    
    private static final Logger logger = LoggerFactory.getLogger(DetteFournisseurService.class);
    
    @Autowired
    private DetteFournisseurRepository detteFournisseurRepository;
    
    @Autowired
    private MouvementRepository mouvementRepository;
    
    @Autowired
    private MouvementService mouvementService;
    
    @Autowired
    private MouvementDetailService mouvementDetailService;
    
    public List<DetteFournisseurView> findAllDetteFournisseur() {
        logger.info("=== RECHERCHE DES DETTES ===");
        List<DetteFournisseurView> result = detteFournisseurRepository.findAllDettes();
        logger.info("Nombre de dettes trouvées : {}", result.size());
        return result;
    }
    
    @Transactional
    public String rembourserDette(DetteFournisseurView detteFournisseur, Double prixRembourser) {
        logger.info("=== REMBOURSEMENT DETTE ===");
        logger.info("ID Achat: {}", detteFournisseur.getIdAchat());
        logger.info("Montant: {}", prixRembourser);
        logger.info("Total Credit: {}", detteFournisseur.getTotalCreditFournisseur());
        logger.info("Total Debit: {}", detteFournisseur.getTotalDebitFournisseur());
        logger.info("Reste à payer: {}", detteFournisseur.getResteAPayer());
        
        try {
            // 1. Vérifier si le montant est valide
            if (prixRembourser == null || prixRembourser <= 0) {
                return " Montant invalide !";
            }
            
            // 2. Vérifier si la dette est déjà complètement payée
            Double totalCredit = detteFournisseur.getTotalCreditFournisseur();
            Double totalDebit = detteFournisseur.getTotalDebitFournisseur();
            Double nouveauDebit = totalDebit + prixRembourser;
            
            if (nouveauDebit > totalCredit) {
                Double reste = totalCredit - totalDebit;
                return "Le montant dépasse le reste à payer ! (Reste: " + 
                       String.format("%.2f", reste) + " Ar)";
            }
            
            // 3. Récupérer le mouvement correspondant à l'achat
            MouvementModel mouvement = mouvementService.findMouvementAchat(
                Long.valueOf(detteFournisseur.getIdAchat())
            );
            
            if (mouvement == null) {
                return " Aucun mouvement trouvé pour l'achat #" + 
                       detteFournisseur.getIdAchat() + " !";
            }
            
            logger.info("Mouvement trouvé: ID={}", mouvement.getId());
            
            // 4. Récupérer le mouvementDetail pour le compte fournisseur (id=2)
            MouvementDetailModel mouvementDetail = mouvementDetailService.findDetailByMouvementAndCompteSingle(
                Long.valueOf(mouvement.getId()),
                2L  // Compte fournisseur (401)
            );
            
            if (mouvementDetail == null) {
                logger.error(" Aucun détail pour le compte fournisseur (id=2)");
                return " Aucun détail de mouvement trouvé pour le compte fournisseur !";
            }
            
            logger.info("MouvementDetail trouvé: ID={}, Debit actuel={}", 
                mouvementDetail.getId(), mouvementDetail.getDebit());
            
            // 5. Mettre à jour le debit du mouvementDetail
            Double nouveauDebitFinal = mouvementDetail.getDebit() + prixRembourser;
            mouvementDetailService.updateDebit(
                Long.valueOf(mouvementDetail.getId()),
                nouveauDebitFinal
            );
            
            logger.info("Debit mis à jour: {} → {}", 
                mouvementDetail.getDebit(), nouveauDebitFinal);
            
            // 6. Enregistrer le paiement (crédit de la caisse)
            mouvementRepository.insertMouvementDetail(
                mouvement.getId(),
                5,  // Compte caisse (571)
                0.00,
                prixRembourser
            );
            
            logger.info("Paiement enregistré: Caisse créditée de {}", prixRembourser);
            
            return "Dette remboursée avec succès ! (Montant: " + 
                   String.format("%.2f", prixRembourser) + " Ar)";
            
        } catch (Exception e) {
            logger.error(" Erreur: {}", e.getMessage());
            return " Erreur: " + e.getMessage();
        }
    }
}