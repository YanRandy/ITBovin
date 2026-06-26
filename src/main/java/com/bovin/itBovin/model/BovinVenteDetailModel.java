package com.bovin.itBovin.model;

import java.math.BigDecimal;

/**
 * Modèle représentant le détail d'un bovin saisi lors d'une vente.
 */
public class BovinVenteDetailModel {
    private Integer idBovin;
    private BigDecimal prixVente;

    // Constructeur par défaut
    public BovinVenteDetailModel() {
    }

    // Constructeur par arguments
    public BovinVenteDetailModel(Integer idBovin, BigDecimal prixVente) {
        this.idBovin = idBovin;
        this.prixVente = prixVente;
    }

    // Getters et Setters
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