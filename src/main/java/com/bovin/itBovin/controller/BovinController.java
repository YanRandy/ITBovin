package com.bovin.itBovin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bovin.itBovin.dto.BovinPayloadDto;
import com.bovin.itBovin.model.BovinModel;
import com.bovin.itBovin.model.LotModel;
import com.bovin.itBovin.model.RaceModel;
import com.bovin.itBovin.service.BovinService;
import com.bovin.itBovin.service.LotService;
import com.bovin.itBovin.service.RaceService;

@Controller
@RequestMapping("/bovin")
public class BovinController {
    @Autowired
    private BovinService bovinService;
    @Autowired
    private RaceService raceService;
    @Autowired
    private LotService lotService;

    // GET /bovin/list
    // - recupere la liste des bovins par BovinService.findAll()
    // - retourne bovin/list.html avec la liste
    @GetMapping("/list")
    public String list(Model model) {
        List<BovinModel> bovins = bovinService.findAll();
        model.addAttribute("bovins", bovins);
        return "bovin/list";
    }

    // GET /bovin/create
    // - retourne bovin/create.html avec la liste des races (RaceService.findAll())
    // et la liste des lots de la race selectionnee (LotService.findAllByRace)
    @GetMapping("/create")
    public String create(
            @RequestParam(value = "idRace", required = false) Long idRace,
            Model model) {

        List<RaceModel> races = raceService.findAll();
        List<LotModel> lots = (idRace != null) ? lotService.findAllByRace(idRace) : List.of();

        model.addAttribute("races", races);
        model.addAttribute("lots", lots);
        model.addAttribute("idRace", idRace);

        return "bovin/create";
    }

    // POST /bovin/create
    // - recupere les infos envoyees en post
    // - verifie l'existence de la race (RaceService.findById)
    // - verifie que date_init < date_arrivee
    // - sauvegarde puis redirige avec message de succes
    @PostMapping("/create")
    public String create(
            @RequestParam("raceId") Integer raceId,
            @RequestParam(value = "lotId", required = false) Integer lotId,
            @RequestParam("poidsInit") Double poidsInit,
            @RequestParam("dateInit") String dateInit,
            @RequestParam("dateArrivee") String dateArrivee,
            Model model,
            RedirectAttributes redirectAttributes) {

        // 1. la race choisie doit exister
        RaceModel race = raceService.findById(raceId);
        if (race == null) {
            model.addAttribute("error", "race choisie introuvable");
            model.addAttribute("races", raceService.findAll());
            model.addAttribute("lots", List.of());
            model.addAttribute("idRace", raceId);
            return "bovin/create";
        }

        // 2. date_init doit etre strictement inferieure a date_arrivee
        if (dateInit.compareTo(dateArrivee) >= 0) {
            model.addAttribute("error", "date initial doit être inferieure à la date_arrivee");
            model.addAttribute("races", raceService.findAll());
            model.addAttribute("lots", lotService.findAllByRace(raceId.longValue()));
            model.addAttribute("idRace", raceId);
            return "bovin/create";
        }

        // 3. construction de l'entite
        BovinModel bovin = new BovinModel();
        bovin.setRace(race);
        bovin.setPoidsInit(poidsInit);
        bovin.setPoidsActuel(poidsInit);
        bovin.setDateInit(java.sql.Date.valueOf(dateInit));
        bovin.setDateArrivee(java.sql.Date.valueOf(dateArrivee));

        if (lotId != null) {
            LotModel lot = new LotModel();
            lot.setId(lotId);
            bovin.setLot(lot);
        }

        // 4. sauvegarde
        bovinService.save(bovin);

        // 5. succes -> on revient sur le formulaire avec un message
        // c'est une facon de faire survivre le message apres un redirect -> apres redirect http
        redirectAttributes.addFlashAttribute("success", "bovin créer avec succès");
        return "redirect:/bovin/create";
    }

    // --- Fonctionnalite distincte : creation en masse de bovins pour un lot
    // existant (hors perimetre de la consigne "Gestion des bovins (creer)") ---

    @GetMapping("/lot/{idLot}/create")
    public String createMany(@PathVariable("idLot") Integer idLot, Model model) {
        model.addAttribute("idLot", idLot);
        model.addAttribute("idRace", raceService.getRaceIdByLotId(idLot));
        return "bovin/create-many";
    }

    @PostMapping("/lot/{idLot}/create")
    public ResponseEntity<String> saveBovins(
            @PathVariable Integer idLot,
            @RequestBody List<BovinPayloadDto> bovins) {

        bovins.forEach(b -> b.setIdLot(idLot));
        bovinService.saveBovinsInLot(bovins);

        return ResponseEntity.ok("succès");
    }
}