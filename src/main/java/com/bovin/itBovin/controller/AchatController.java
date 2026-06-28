package com.bovin.itBovin.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bovin.itBovin.dto.AchatBovinDto;
import com.bovin.itBovin.model.AlimentModel;
import com.bovin.itBovin.model.FournisseurModel;
import com.bovin.itBovin.service.AchatService;
import com.bovin.itBovin.service.AlimentService;
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

    @Autowired
    private AlimentService alimentService;

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


    @GetMapping("/aliment/{id_fournisseur}")
    public String achatAliment(Model model,
                           @PathVariable("id_fournisseur") Integer idFournisseur) {

        Optional<FournisseurModel> optionalFournisseur =
            fournisseurService.findById(idFournisseur);

   
        if (optionalFournisseur.isEmpty()) {
            return "redirect:/fournisseur/list?error=fournisseur_introuvable";
        }


        FournisseurModel fournisseur = optionalFournisseur.get();

        model.addAttribute("fournisseur", fournisseur);

    // liste des aliments
        model.addAttribute("aliments", alimentService.findAll());

        return "achat/aliment";
    }

    @PostMapping("/aliment")
    public String achatAliment(@RequestParam("id_fournisseur") Integer idFournisseur,
                               @RequestParam("id_aliment") Integer idAliment,
                               @RequestParam("quantite") Double quantite,
                               @RequestParam("prix_unitaire") Double prixUnitaire,
                               Model model) {

        try {
            // =========================
            // 1. FIND FOURNISSEUR
            // =========================
            Optional<FournisseurModel> optionalFournisseur =
                    fournisseurService.findById(idFournisseur);

            if (optionalFournisseur.isEmpty()) {
                return "redirect:/fournisseur/list?error=fournisseur_introuvable";
            }

            FournisseurModel fournisseur = optionalFournisseur.get();

            // =========================
            // 2. FIND ALIMENT
            // =========================
            Optional<AlimentModel> optionalAliment =
                    alimentService.findById(idAliment);

            if (optionalAliment.isEmpty()) {
                return "redirect:/achat/aliment/" + idFournisseur
                        + "?error=aliment_introuvable";
            }

            AlimentModel aliment = optionalAliment.get();

            // =========================
            // 3. CALL SERVICE ACHAT
            // =========================
            achatService.achatAliment(
                    aliment,
                    fournisseur,
                    quantite,
                    prixUnitaire
            );

            // =========================
            // 4. SUCCESS
            // =========================
            return "redirect:/achat/aliment/" + idFournisseur
                    + "?success=achat_reussi";

        } catch (Exception e) {
    System.out.println(e.getMessage());
    e.printStackTrace();
    return "redirect:/achat/aliment/" + idFournisseur
            + "?error=erreur_achat";
}
    }
    
}
