package com.bovin.itBovin.model;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "v_dette_fournisseur")
@Immutable
public class DetteFournisseurView {
    
    @Id
    @Column(name = "id_achat")
    private Integer idAchat;
    
    @Column(name = "fournisseur_nom")
    private String fournisseurNom;
    
    @Column(name = "achat_description")
    private String achatDescription;
    
    @Column(name = "total_credit_fournisseur")
    private Double totalCreditFournisseur;
    
    @Column(name = "total_debit_fournisseur")
    private Double totalDebitFournisseur;
    
    @Column(name = "reste_a_payer")
    private Double resteAPayer;
    
    // Getters et Setters
    public Integer getIdAchat() { return idAchat; }
    public void setIdAchat(Integer idAchat) { this.idAchat = idAchat; }
    
    public String getFournisseurNom() { return fournisseurNom; }
    public void setFournisseurNom(String fournisseurNom) { this.fournisseurNom = fournisseurNom; }
    
    public String getAchatDescription() { return achatDescription; }
    public void setAchatDescription(String achatDescription) { this.achatDescription = achatDescription; }
    
    public Double getTotalCreditFournisseur() { return totalCreditFournisseur; }
    public void setTotalCreditFournisseur(Double totalCreditFournisseur) { 
        this.totalCreditFournisseur = totalCreditFournisseur; 
    }
    
    public Double getTotalDebitFournisseur() { return totalDebitFournisseur; }
    public void setTotalDebitFournisseur(Double totalDebitFournisseur) { 
        this.totalDebitFournisseur = totalDebitFournisseur; 
    }
    
    public Double getResteAPayer() { return resteAPayer; }
    public void setResteAPayer(Double resteAPayer) { this.resteAPayer = resteAPayer; }
}