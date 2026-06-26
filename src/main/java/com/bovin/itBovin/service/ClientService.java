package com.bovin.itBovin.service;

import org.springframework.data.domain.Page;
import com.bovin.itBovin.model.ClientModel;
import java.util.Optional; // <-- AJOUTE CETTE LIGNE

public interface ClientService {
    Page<ClientModel> getClients(String nom, String adresse, String contact, int page, int size, String sortBy, String sortDir);
    
    ClientModel getClientById(Integer id);
}