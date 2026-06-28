package com.bovin.itBovin.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "achat_aliment_detail")
public class AchatAlimentDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // =========================
    // Relations
    // =========================

    @ManyToOne
    @JoinColumn(name = "id_achat", nullable = false)
    private AchatModel achat;

    @ManyToOne
    @JoinColumn(name = "id_aliment", nullable = false)
    private AlimentModel aliment;

    // =========================
    // Attributs métier
    // =========================

    @Column(nullable = false)
    private BigDecimal quantite;

    @Column(name = "prix_unitaire", nullable = false)
    private BigDecimal prixUnitaire;

    // champ GENERATED (lecture seule)
    @Column(name = "prix_total", insertable = false, updatable = false)
    private BigDecimal prixTotal;

    // =========================
    // Getters / Setters
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AchatModel getAchat() {
        return achat;
    }

    public void setAchat(AchatModel achat) {
        this.achat = achat;
    }

    public AlimentModel getAliment() {
        return aliment;
    }

    public void setAliment(AlimentModel aliment) {
        this.aliment = aliment;
    }

    public BigDecimal getQuantite() {
        return quantite;
    }

    public void setQuantite(BigDecimal quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }
}
