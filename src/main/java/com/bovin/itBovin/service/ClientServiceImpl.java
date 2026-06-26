package com.bovin.itBovin.service;

import com.bovin.itBovin.model.ClientModel;
import com.bovin.itBovin.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Page<ClientModel> getClients(String nom, String adresse, String contact, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return clientRepository.findAll(pageable);
    }

    // Ajoute cette implémentation :
    @Override
    public ClientModel getClientById(Integer id) {
        return clientRepository.findById(id).orElse(null);
    }

    
    public List<ClientModel> findAllClients() {
        return clientRepository.findAll();
    }

}