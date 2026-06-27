package com.bovin.itBovin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bovin.itBovin.model.DetteFournisseurView;
import com.bovin.itBovin.service.DetteFournisseurService;

@RestController
@RequestMapping("/api/dette")
public class DetteFournisseurRestController {
    
    private static final Logger logger = LoggerFactory.getLogger(DetteFournisseurRestController.class);
    
    @Autowired
    private DetteFournisseurService detteFournisseurService;
    
    @GetMapping("/liste")
    public List<DetteFournisseurView> listeDettes() {
        return detteFournisseurService.findAllDetteFournisseur();
    }
    
    @PostMapping("/rembourser")
    public Map<String, Object> rembourser(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            logger.info("=== PAYLOAD RECU ===");
            logger.info("Payload complet: {}", payload);
            logger.info("Clés du payload: {}", payload.keySet());
            
            // Récupérer l'ID Achat
            Object idAchatObj = payload.get("idAchat");
            
            // Si pas trouvé avec "idAchat", essayer avec "id_achat"
            if (idAchatObj == null) {
                idAchatObj = payload.get("id_achat");
                logger.info("Tentative avec 'id_achat': {}", idAchatObj);
            }
            
            // Vérifier que l'ID existe
            if (idAchatObj == null) {
                logger.error(" ID Achat manquant dans le payload !");
                response.put("success", false);
                response.put("message", " ID Achat manquant ! (clé attendue: 'idAchat')");
                return response;
            }
            
            logger.info("ID Achat brut: {} (type: {})", idAchatObj, idAchatObj.getClass().getName());
            
            // Convertir l'ID en Integer
            Integer idAchat;
            try {
                if (idAchatObj instanceof Integer) {
                    idAchat = (Integer) idAchatObj;
                } else if (idAchatObj instanceof String) {
                    idAchat = Integer.parseInt((String) idAchatObj);
                } else {
                    idAchat = Integer.parseInt(idAchatObj.toString());
                }
            } catch (Exception e) {
                logger.error("Erreur conversion ID: {}", e.getMessage());
                response.put("success", false);
                response.put("message", " Format de l'ID Achat invalide: " + idAchatObj);
                return response;
            }
            
            logger.info("ID Achat converti: {}", idAchat);
            
            // Récupérer le montant
            Object montantObj = payload.get("montant");
            
            if (montantObj == null) {
                logger.error(" Montant manquant !");
                response.put("success", false);
                response.put("message", " Montant manquant !");
                return response;
            }
            
            logger.info("Montant brut: {} (type: {})", montantObj, montantObj.getClass().getName());
            
            // Convertir le montant en Double
            Double montant;
            try {
                if (montantObj instanceof Double) {
                    montant = (Double) montantObj;
                } else if (montantObj instanceof Integer) {
                    montant = ((Integer) montantObj).doubleValue();
                } else if (montantObj instanceof String) {
                    montant = Double.parseDouble((String) montantObj);
                } else {
                    montant = Double.parseDouble(montantObj.toString());
                }
            } catch (Exception e) {
                logger.error("Erreur conversion montant: {}", e.getMessage());
                response.put("success", false);
                response.put("message", " Format du montant invalide: " + montantObj);
                return response;
            }
            
            logger.info("Montant converti: {}", montant);
            logger.info("ID Achat: {}, Montant: {}", idAchat, montant);
            
            // Récupérer toutes les dettes
            List<DetteFournisseurView> dettes = detteFournisseurService.findAllDetteFournisseur();
            logger.info("Nombre de dettes trouvées: {}", dettes.size());
            
            // Afficher tous les IDs disponibles
            for (DetteFournisseurView d : dettes) {
                logger.info("Dette disponible - ID Achat: {}", d.getIdAchat());
            }
            
            // Chercher la dette correspondante
            DetteFournisseurView dette = null;
            for (DetteFournisseurView d : dettes) {
                if (d.getIdAchat().equals(idAchat)) {
                    dette = d;
                    break;
                }
            }
            
            if (dette == null) {
                logger.warn(" Dette non trouvée pour l'achat ID: {}", idAchat);
                response.put("success", false);
                response.put("message", " Dette non trouvée pour l'achat #" + idAchat + " !");
                return response;
            }
            
            logger.info(" Dette trouvée: Reste à payer = {}", dette.getResteAPayer());
            
            // Vérifier le montant
            if (montant <= 0) {
                response.put("success", false);
                response.put("message", " Montant invalide !");
                return response;
            }
            
            if (montant > dette.getResteAPayer()) {
                response.put("success", false);
                response.put("message", " Montant trop élevé ! Reste: " + 
                           String.format("%.2f", dette.getResteAPayer()) + " Ar");
                return response;
            }
            
            // Appeler la méthode de remboursement
            String resultat = detteFournisseurService.rembourserDette(dette, montant);
            
            if (resultat.startsWith("")) {
                response.put("success", true);
                response.put("message", resultat);
            } else {
                response.put("success", false);
                response.put("message", resultat);
            }
            
        } catch (Exception e) {
            logger.error(" Erreur générale: {}", e.getMessage());
            e.printStackTrace();
            response.put("success", false);
            response.put("message", " Erreur: " + e.getMessage());
        }
        
        return response;
    }
}