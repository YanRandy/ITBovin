package com.bovin.itBovin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bovin.itBovin.model.MouvementModel;
import com.bovin.itBovin.repository.MouvementRepository;

@Service
public class MouvementService {
    
    private static final Logger logger = LoggerFactory.getLogger(MouvementService.class);
    
    @Autowired
    private MouvementRepository mouvementRepository;
    
    public MouvementModel findMouvementAchat(Long idAchat) {
        logger.info("Recherche du mouvement pour l'achat ID: {}", idAchat);
        MouvementModel result = mouvementRepository.findMouvementAchat(idAchat);
        if (result != null) {
            logger.info(" Mouvement trouvé: ID={}", result.getId());
        } else {
            logger.warn(" Aucun mouvement trouvé pour l'achat ID: {}", idAchat);
        }
        return result;
    }
}