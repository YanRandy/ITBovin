package com.bovin.itBovin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bovin.itBovin.model.BovinModel;
import com.bovin.itBovin.service.BovinService;

@Controller
@RequestMapping("/bovin")
public class BovinController {
    @Autowired
    private BovinService BovinService;

    @GetMapping("/liste")
    public String liste(Model model) {
        List<BovinModel> bovins = BovinService.getAllBovins();

        // on le met dans le model pour pouvoir l'afficher dans la vue
        model.addAttribute("bovins", bovins);
        return "bovin/liste";
    }
}
