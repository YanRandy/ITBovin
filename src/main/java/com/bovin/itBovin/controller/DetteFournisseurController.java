package com.bovin.itBovin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bovin.itBovin.model.DetteFournisseurView;
import com.bovin.itBovin.service.DetteFournisseurService;

@Controller
@RequestMapping("/dette")
public class DetteFournisseurController {
    
    private static final Logger logger = LoggerFactory.getLogger(DetteFournisseurController.class);
    
    @Autowired
    private DetteFournisseurService detteFournisseurService;
    
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return " Le contrôleur DetteFournisseur fonctionne !";
    }
    
    @GetMapping("/debug")
    @ResponseBody
    public String debug() {
        try {
            List<DetteFournisseurView> dettes = detteFournisseurService.findAllDetteFournisseur();
            return "Dettes trouvées : " + dettes.size() + "\n" + dettes.toString();
        } catch (Exception e) {
            return " Erreur : " + e.getMessage();
        }
    }
    
    @GetMapping("/fournisseur")
    public String detteFournisseurs(Model model) {
        logger.info("=== AFFICHAGE DES DETTES FOURNISSEURS ===");
        try {
            List<DetteFournisseurView> dettes = detteFournisseurService.findAllDetteFournisseur();
            logger.info("Nombre de dettes trouvées : {}", dettes.size());
            model.addAttribute("dettes", dettes);
        } catch (Exception e) {
            logger.error("Erreur: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
        }
        return "dette/fournisseur";
    }
}