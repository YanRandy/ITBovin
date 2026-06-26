package com.bovin.itBovin.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "caisse")
public class CaisseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String libelle;

    @Column(name = "solde_initial", precision = 12, scale = 2)
    private BigDecimal soldeInitial = BigDecimal.ZERO;

    @Column(name = "solde_actuelle", precision = 12, scale = 2)
    private BigDecimal soldeActuelle = BigDecimal.ZERO;

    // Constructeur par défaut
    public CaisseModel() {
    }

    // Constructeur avec paramètres
    public CaisseModel(String libelle, BigDecimal soldeInitial, BigDecimal soldeActuelle) {
        this.libelle = libelle;
        this.soldeInitial = soldeInitial;
        this.soldeActuelle = soldeActuelle;
    }

    // Getters et setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public BigDecimal getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(BigDecimal soldeInitial) {
        this.soldeInitial = soldeInitial;
    }

    public BigDecimal getSoldeActuelle() {
        return soldeActuelle;
    }

    public void setSoldeActuelle(BigDecimal soldeActuelle) {
        this.soldeActuelle = soldeActuelle;
    }

    // Méthode pour mettre à jour le solde (utile pour les opérations de caisse)
    public void ajouterMontant(BigDecimal montant) {
        if (montant != null && montant.compareTo(BigDecimal.ZERO) > 0) {
            this.soldeActuelle = this.soldeActuelle.add(montant);
        }
    }

    public void retirerMontant(BigDecimal montant) {
        if (montant != null && montant.compareTo(BigDecimal.ZERO) > 0) {
            this.soldeActuelle = this.soldeActuelle.subtract(montant);
        }
    }

    @Override
    public String toString() {
        return "CaisseModel{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", soldeInitial=" + soldeInitial +
                ", soldeActuelle=" + soldeActuelle +
                '}';
    }
}