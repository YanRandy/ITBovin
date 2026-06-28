package com.bovin.itBovin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bovin.itBovin.model.FournisseurModel;
import com.bovin.itBovin.service.FournisseurService;

@Controller
@RequestMapping("/fournisseur")
public class FournisseurController {
    
    @Autowired
    private FournisseurService fournisseurService;

    @GetMapping("/list")
    public String list(Model model) {
        List<FournisseurModel> fournisseur = fournisseurService.findAll();
        model.addAttribute("fournisseurs", fournisseur);
        return "fournisseur/list";
    }
}