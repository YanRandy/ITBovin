package com.bovin.itBovin.controller;

import com.bovin.itBovin.dto.VenteDTO;
import com.bovin.itBovin.dto.VenteDetailDTO;
import com.bovin.itBovin.err.BovinNotFoundErr;
import com.bovin.itBovin.err.ClientNotFoundErr;
import com.bovin.itBovin.dto.BovinDTO;
import com.bovin.itBovin.dto.PaiementDTO;
import com.bovin.itBovin.model.*;
import com.bovin.itBovin.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

@Controller
@RequestMapping("/vente")
public class VenteController {

    @Autowired
    private  VenteService venteService;
    @Autowired
    private  ClientServiceImpl clientService;
    @Autowired
    private  BovinService bovinService;
    @Autowired
    private  LotService lotService;
    @Autowired
    private  CaisseService caisseService;

   

    @GetMapping("/form")
    public String showForm(Model model) {
        // Créer un DTO vide avec une ligne de détail et un paiement par défaut
        VenteDTO venteDTO = new VenteDTO();
        venteDTO.setDateSaisie(LocalDateTime.now());
        // Ajouter une ligne de détail vide
        VenteDetailDTO detailDTO = new VenteDetailDTO();
        venteDTO.getDetails().add(detailDTO);
        // Ajouter un paiement vide
        PaiementDTO paiementDTO = new PaiementDTO();
        paiementDTO.setDatePaiement(LocalDateTime.now());
        venteDTO.getPaiements().add(paiementDTO);

        model.addAttribute("venteDTO", venteDTO);
        model.addAttribute("clients", clientService.findAllClients());
        model.addAttribute("bovins", bovinService.getAllBovins());
        model.addAttribute("lots", lotService.findAll());
        model.addAttribute("caisses", caisseService.findAll());
        return "vente/form";
    }

  @PostMapping("/save")
    public String saveVente(@ModelAttribute VenteDTO venteDTO) throws ClientNotFoundErr {
        // Conversion des détails
        List<BovinVenteDetailModel> bovinsVendus = venteDTO.getDetails().stream()
                .filter(d -> d.getIdBovin() != null)
                .map(d -> new BovinVenteDetailModel(d.getIdBovin(), d.getPrixVente()))
                .collect(Collectors.toList());

        venteService.enregistrerVente(
                venteDTO.getIdClient(),
                venteDTO.getDescription(),
                venteDTO.getDateSaisie() != null ? venteDTO.getDateSaisie() : LocalDateTime.now(),
                bovinsVendus,
                venteDTO.getPaiements()
        );
        return "redirect:/clients";
    }

    // ============ Endpoints AJAX ============

    @GetMapping("/bovin/{id}/detail")
    @ResponseBody
    public BovinDTO getBovinDetail(@PathVariable Integer id) throws BovinNotFoundErr {
        BovinModel bovin = bovinService.findByLotId(id);
        // Convertir en DTO (simplifié)
        BovinDTO dto = new BovinDTO();
        dto.setId(bovin.getId());
        dto.setPoidsActuel(bovin.getPoidsActuel());
        if (bovin.getLot() != null) {
            dto.setLotId(bovin.getLot().getId());
        }
        if (bovin.getRace() != null) {
            dto.setRaceLibelle(bovin.getRace().getLibelle());
        }
        return dto;
    }

    @GetMapping("/lot/{id}/bovins")
    @ResponseBody
    public List<BovinDTO> getBovinsByLot(@PathVariable Integer id) {
        List<BovinModel> bovins = bovinService.getBovinsByLot(id);
        return bovins.stream().map(b -> {
            BovinDTO dto = new BovinDTO();
            dto.setId(b.getId());
            dto.setPoidsActuel(b.getPoidsActuel());
            if (b.getRace() != null) {
                dto.setRaceLibelle(b.getRace().getLibelle());
            }
            return dto;
        }).collect(Collectors.toList());
    }
    
    @GetMapping("/nouvelle")
    public String showForm(@RequestParam(required = false) Integer idClient, Model model) {
        VenteDTO venteDTO = new VenteDTO();
        
        if (idClient != null) {
            venteDTO.setIdClient(idClient);
        }
        
        // 1. Ajouter une ligne de détail vide (Bovins)
        VenteDetailDTO detailDTO = new VenteDetailDTO();
        venteDTO.getDetails().add(detailDTO);
        
        // 2. RÉACTIVATION : Ajouter un paiement vide par défaut (indispensable pour afficher la caisse !)
        PaiementDTO paiementDTO = new PaiementDTO();
        paiementDTO.setDatePaiement(LocalDateTime.now());
        venteDTO.getPaiements().add(paiementDTO); // <-- C'est cette ligne qui fait réapparaître le bloc HTML
        
        model.addAttribute("venteDTO", venteDTO);
        
        // 3. Alimentation complète des listes pour Thymeleaf
        model.addAttribute("clients", clientService.getClients("", "", "", 0, 1000, "nom", "asc").getContent());
        model.addAttribute("bovins", bovinService.getAllBovins());
        model.addAttribute("lots", lotService.findAll());         
        model.addAttribute("caisses", caisseService.findAll());      
        
        return "vente/form";
    }
}