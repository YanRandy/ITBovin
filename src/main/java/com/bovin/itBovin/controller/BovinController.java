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

import com.bovin.itBovin.dto.BovinPayloadDto;
import com.bovin.itBovin.model.BovinModel;
import com.bovin.itBovin.service.BovinService;
import com.bovin.itBovin.service.RaceService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/bovin")
public class BovinController {
    @Autowired
    private BovinService BovinService;
    @Autowired
    private RaceService raceService;

    @GetMapping("/liste")
    public String liste(Model model) {
        List<BovinModel> bovins = BovinService.getAllBovins();

        // on le met dans le model pour pouvoir l'afficher dans la vue
        model.addAttribute("bovins", bovins);
        return "bovin/liste";
    }

    @GetMapping("/lot/{idLot}/save")
    public String save(@PathVariable("idLot") Integer idLot, Model model) {
        model.addAttribute("idLot", idLot);
        // ici on doit prendre la race du lot
        model.addAttribute("idRace", raceService.getRaceIdByLotId(idLot));
        return "bovin/save";
    }

    @PostMapping("/lot/{idLot}/save")
    public ResponseEntity<String> saveBovins(@PathVariable("idLot") Integer idLot,
            @RequestBody List<BovinPayloadDto> bovins) {
        if (!bovins.isEmpty()) {
            System.out.println(bovins.get(0).idLot()); // Fonctionnera parfaitement
            BovinService.saveBovinsInLot(bovins);
        }
        return ResponseEntity.ok("succès");
    }
}
