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
import com.bovin.itBovin.model.LotModel;
import com.bovin.itBovin.model.RaceModel;
import com.bovin.itBovin.service.LotService;
import com.bovin.itBovin.service.RaceService;
import com.google.gson.Gson;

@Controller
@RequestMapping("/lot")
public class LotController {
    @Autowired
    private LotService lotService;
    @Autowired
    private RaceService raceService;

    // list
    @GetMapping("/list") 
    public String List(Model model) {
        List<LotModel> lots = lotService.findAll();

        model.addAttribute("title", "Lot - list");
        model.addAttribute("lots", lots);
        return "lot/list";
    }

    // save
    @GetMapping("/create")
    public String create(Model model) {
        // get all race
        List<RaceModel> races = raceService.getAllRaces();

        model.addAttribute("races", races);
        model.addAttribute("title", "Lot - Create");

        return "lot/create";
    }
}
