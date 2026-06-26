package com.bovin.itBovin.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaiementDTO {
    private String libelle;
    private BigDecimal montant;
    private LocalDateTime datePaiement;
    private Integer idCaisse;
    // getters/setters
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
    public BigDecimal getMontant() {
        return montant;
    }
    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }
    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }
    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }
    public Integer getIdCaisse() {
        return idCaisse;
    }
    public void setIdCaisse(Integer idCaisse) {
        this.idCaisse = idCaisse;
    }
}