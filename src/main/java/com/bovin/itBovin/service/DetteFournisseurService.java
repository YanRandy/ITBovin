package com.bovin.itBovin.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bovin.itBovin.model.DetteFournisseurView;
import com.bovin.itBovin.repository.DetteFournisseurRepository;
import com.bovin.itBovin.repository.MouvementRepository;

@Service
public class DetteFournisseurService {
    
    private static final Logger logger = LoggerFactory.getLogger(DetteFournisseurService.class);
    
    @Autowired
    private DetteFournisseurRepository detteFournisseurRepository;
    
    @Autowired
    private MouvementRepository mouvementRepository;
    
    public List<DetteFournisseurView> findAllDetteFournisseur() {
        logger.info("=== RECHERCHE DES DETTES ===");
        List<DetteFournisseurView> result = detteFournisseurRepository.findAllDettes();
        logger.info("Nombre de dettes trouvées : {}", result.size());
        return result;
    }
    
    @Transactional
    public boolean rembourserDette(Integer idAchat, Double montant) {
        logger.info("Remboursement de {} Ar pour l'achat {}", montant, idAchat);
        
        try {
            // 1. Insérer un mouvement de paiement
            int mouvementId = mouvementRepository.insertMouvement(1, idAchat, LocalDate.now());
            
            // 2. Insérer les détails du paiement
            // Débit du compte fournisseur (réduction de la dette)
            mouvementRepository.insertMouvementDetail(mouvementId, 2, montant, 0.00);
            
            // Crédit du compte caisse (l'argent sort)
            mouvementRepository.insertMouvementDetail(mouvementId, 5, 0.00, montant);
            
            logger.info("Remboursement effectué avec succès !");
            return true;
            
        } catch (Exception e) {
            logger.error("Erreur lors du remboursement: {}", e.getMessage());
            return false;
        }
    }
}