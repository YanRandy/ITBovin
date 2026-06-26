package com.bovin.itBovin.dto;

import java.math.BigDecimal;

public class VenteDetailDTO {
    private Integer idBovin;
    private BigDecimal prixVente;
    // getters/setters
    public Integer getIdBovin() {
        return idBovin;
    }
    public void setIdBovin(Integer idBovin) {
        this.idBovin = idBovin;
    }
    public BigDecimal getPrixVente() {
        return prixVente;
    }
    public void setPrixVente(BigDecimal prixVente) {
        this.prixVente = prixVente;
    }
}