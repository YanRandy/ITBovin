package com.bovin.itBovin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bovin.itBovin.model.DetteFournisseurView;
import com.bovin.itBovin.service.DetteFournisseurService;

@RestController
@RequestMapping("/api/dette")
public class DetteFournisseurRestController {
    
    @Autowired
    private DetteFournisseurService detteFournisseurService;
    
    @GetMapping("/liste")
    public List<DetteFournisseurView> listeDettes() {
        return detteFournisseurService.findAllDetteFournisseur();
    }
}