package com.bovin.itBovin.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.bovin.itBovin.model.RaceModel;
import com.bovin.itBovin.service.RaceService;

@Controller
public class RaceController {
    private final RaceService raceService;

    public RaceController(RaceService raceService) {
        this.raceService = raceService;
    }

    @GetMapping("/races")
    public String listRaces(Model model) {
        model.addAttribute("races", raceService.getAllRaces());
        return "race/list";
    }

    @GetMapping("/races/create")
    public String showCreateForm(Model model) {
        model.addAttribute("race", new RaceModel());
        return "race/create";
    }

    @PostMapping("/races")
    public String createRace(@ModelAttribute RaceModel race) {
        raceService.createRace(race);
        return "redirect:/races";
    }

    @PostMapping("/races/delete/{id_race}")
    public String deleteRace(@PathVariable Integer id_race) {
        raceService.deleteById(id_race);
        return "redirect:/races";
    }
}
