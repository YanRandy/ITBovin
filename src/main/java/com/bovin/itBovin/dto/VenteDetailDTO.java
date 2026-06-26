package com.bovin.itBovin.dto;

public class VenteDetailDTO {

    private Long id; // peut être null pour les nouvelles lignes
    private Long idBovin;
    private String bovinInfo; // pour affichage (ex: "Bovin #123 - Race: ...")
    private Double prixVente;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getIdBovin() {
        return idBovin;
    }
    public void setIdBovin(Long idBovin) {
        this.idBovin = idBovin;
    }
    public String getBovinInfo() {
        return bovinInfo;
    }
    public void setBovinInfo(String bovinInfo) {
        this.bovinInfo = bovinInfo;
    }
    public Double getPrixVente() {
        return prixVente;
    }
    public void setPrixVente(Double prixVente) {
        this.prixVente = prixVente;
    }

    // Constructeurs, getters, setters
}