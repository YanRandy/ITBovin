package com.bovin.itBovin.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VenteDTO {

    private Long id;
    private Long idClient;
    private String nomClient; // pour affichage
    private LocalDateTime dateSaisie;
    private String description;
    private Double montantTotalVente;
    private List<VenteDetailDTO> details = new ArrayList<>();
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getIdClient() {
        return idClient;
    }
    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }
    public String getNomClient() {
        return nomClient;
    }
    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }
    public LocalDateTime getDateSaisie() {
        return dateSaisie;
    }
    public void setDateSaisie(LocalDateTime dateSaisie) {
        this.dateSaisie = dateSaisie;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getMontantTotalVente() {
        return montantTotalVente;
    }
    public void setMontantTotalVente(Double montantTotalVente) {
        this.montantTotalVente = montantTotalVente;
    }
    public List<VenteDetailDTO> getDetails() {
        return details;
    }
    public void setDetails(List<VenteDetailDTO> details) {
        this.details = details;
    }

    // Constructeurs, getters, setters
}