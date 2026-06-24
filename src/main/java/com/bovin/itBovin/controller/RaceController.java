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

    @GetMapping("/race/list")
    public String list(Model model) {
        model.addAttribute("races", raceService.getAllRaces());
        return "race/list";
    }

    @GetMapping("/race/create")
    public String create(Model model) {
        model.addAttribute("race", new RaceModel());
        return "race/create";
    }

    @PostMapping("/race/create")
    public String create(@ModelAttribute RaceModel race) {
        raceService.save(race);
        return "redirect:/race/list";
    }

    @PostMapping("/race/delete/{id_race}")
    public String delete(@PathVariable Integer id_race) {
        RaceModel race = raceService.findById(id_race);
        if (race == null) {
            return "redirect:/race/list";
        }
        try {
            raceService.deleteById(id_race);
        } catch (Exception e) {
            return "redirect:/race/list";
        }
        return "redirect:/race/list";
    }

    @GetMapping("/race/edit/{id_race}")
    public String edit(@PathVariable Integer id_race, Model model) {
        RaceModel race = raceService.findById(id_race);
        if (race == null) {
            return "redirect:/race/list";
        }
        model.addAttribute("race", race);
        return "race/edit";
    }

    @PostMapping("/race/edit")
    public String edit(@ModelAttribute RaceModel race) {
        RaceModel existingRace = raceService.findById(race.getId());
        if (existingRace == null) {
            return "redirect:/race/list";
        }
        raceService.save(race);
        return "redirect:/race/list";
    }
}
