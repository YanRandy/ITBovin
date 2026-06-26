package com.bovin.itBovin.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bovin.itBovin.dto.VenteDTO;

@Controller
@RequestMapping("/vente")
public class VenteController {
    @GetMapping("/form")
    public String showForm(Model model) {
        model.addAttribute("venteDTO", new VenteDTO());
        model.addAttribute("clients", new ArrayList<>());
        model.addAttribute("bovins", new ArrayList<>());
        model.addAttribute("lots", new ArrayList<>());
        return "vente/form";
    }
}
