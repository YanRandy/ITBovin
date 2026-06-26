package com.bovin.itBovin.controller;

import com.bovin.itBovin.dto.ClientSearchCriteria;
import com.bovin.itBovin.model.ClientModel;
import com.bovin.itBovin.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public String listClients(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String adresse,
            @RequestParam(required = false) String contact,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        // Construire les critères
        ClientSearchCriteria criteria = new ClientSearchCriteria();
        criteria.setNom(nom);
        criteria.setAdresse(adresse);
        criteria.setContact(contact);

        // Pagination et tri
        Sort sort = sortDir.equalsIgnoreCase("asc") 
                ? Sort.by(sortBy).ascending() 
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Appel du service
        Page<ClientModel> clientPage = clientService.getClientsWithFilter(criteria, pageable);

        // Ajout au modèle pour la vue
        model.addAttribute("clientPage", clientPage);
        model.addAttribute("nom", nom);
        model.addAttribute("adresse", adresse);
        model.addAttribute("contact", contact);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);

        return "vente/client/list"; // vue clients.html
    }
}