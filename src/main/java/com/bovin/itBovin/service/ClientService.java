package com.bovin.itBovin.service;

import com.bovin.itBovin.model.ClientModel;
import org.springframework.data.domain.Page;

public interface ClientService {
    Page<ClientModel> getClients(String nom, String adresse, String contact, int page, int size, String sortBy, String sortDir);
    
    ClientModel getClientById(Integer id);
}