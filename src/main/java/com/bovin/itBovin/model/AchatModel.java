package com.bovin.itBovin.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "achat")
public class AchatModel {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "id_fournisseur")
    private FournisseurModel fournisseur;

    @Column(name = "date_achat")
    private Date dateAchat;

    @Column(name = "description")
    private String description;

    public AchatModel() {
    }

    public AchatModel(FournisseurModel fournisseur, Date dateAchat, String description) {
        this.fournisseur = fournisseur;
        this.dateAchat = dateAchat;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FournisseurModel getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(FournisseurModel fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Date getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
