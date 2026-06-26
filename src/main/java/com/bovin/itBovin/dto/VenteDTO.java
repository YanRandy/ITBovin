package com.bovin.itBovin.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VenteDTO {
    private Integer idClient;
    private LocalDateTime dateSaisie;
    private String description;
    private List<VenteDetailDTO> details = new ArrayList<>();
    private List<PaiementDTO> paiements = new ArrayList<>();
    // getters/setters
    public Integer getIdClient() {
        return idClient;
    }
    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
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
    public List<VenteDetailDTO> getDetails() {
        return details;
    }
    public void setDetails(List<VenteDetailDTO> details) {
        this.details = details;
    }
    public List<PaiementDTO> getPaiements() {
        return paiements;
    }
    public void setPaiements(List<PaiementDTO> paiements) {
        this.paiements = paiements;
    }
}