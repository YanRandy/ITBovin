package com.bovin.itBovin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bovin.itBovin.dto.AchatBovinDto;
import com.bovin.itBovin.model.FournisseurModel;
import com.bovin.itBovin.service.AchatService;
import com.bovin.itBovin.service.FournisseurService;
import com.bovin.itBovin.service.RaceService;

@Controller
@RequestMapping("/achat")
public class AchatController {

    @Autowired
    private FournisseurService fournisseurService;

    @Autowired
    private RaceService raceService;

    @Autowired
    private AchatService achatService;

    @GetMapping("/bovin/{id_fournisseur}")
    public String achatBovin(Model model, @PathVariable("id_fournisseur") Integer idFournisseur) {
        Optional<FournisseurModel> optionalFournisseur = fournisseurService.findById(idFournisseur);

        if (optionalFournisseur.isEmpty()) {
            model.addAttribute("message", "Fournisseur introuvable");
            return "redirect:/fournisseur/list";
        } else {
            model.addAttribute("fournisseur", optionalFournisseur.get());
            model.addAttribute("races", raceService.getAllRaces());
            return "achat/bovin";
        }
    }

    @PostMapping("/bovin")
    public String achatBovin(AchatBovinDto achatBovinDto,RedirectAttributes redirectAttributes) {

        // verifier si le fournisseur existe
        if (fournisseurService.findById(achatBovinDto.idFournisseur()).isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Fournisseur introuvable");
            return "redirect:/fournisseur/list";
        }

        try {
            this.achatService.acheterBovin(achatBovinDto);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Une erreur s ' est produit lors de l achat de bovin : " + e.getMessage());
            return "redirect:/achat/bovin/" + achatBovinDto.idFournisseur();
        }

        redirectAttributes.addFlashAttribute("message", "Bovin ajouter avec succes");
        return "redirect:/achat/bovin/" + achatBovinDto.idFournisseur();
    }
}
