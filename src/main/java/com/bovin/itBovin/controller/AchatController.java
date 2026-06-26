package com.bovin.itBovin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bovin.itBovin.model.FournisseurModel;
import com.bovin.itBovin.service.FournisseurService;
import com.bovin.itBovin.service.RaceService;

@Controller
@RequestMapping("/achat")
public class AchatController {
    
    @Autowired
    private FournisseurService fournisseurService;

    @Autowired
    private RaceService raceService;

    @GetMapping("/bovin/{id_fournisseur}")
    public String achatBovin(Model model,@PathVariable("id_fournisseur")  Integer idFournisseur) {
        Optional<FournisseurModel> optionalFournisseur =  fournisseurService.findById(idFournisseur);

        if(optionalFournisseur.isEmpty()) {
            model.addAttribute("message","Fournisseur introuvable");
            return "redirect:/fournisseur/list";
        } else {
            model.addAttribute("fournisseur",optionalFournisseur.get());
            model.addAttribute("races",raceService.getAllRaces());
            return "achat/bovin";
        }
    }
}
