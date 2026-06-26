package com.bovin.itBovin.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "historique_bovin")
public class HistoriqueBovinModel {

    @Id
    @Column(name = "id_bovin")
    private Integer idBovin; // référence au bovin original (clé primaire)

    @Column(name = "poids_au_moment_vente", precision = 6, scale = 2)
    private BigDecimal poidsAuMomentVente;

    @Column(name = "date_sortie_vente")
    private LocalDateTime dateSortieVente = LocalDateTime.now();

    // Constructeur par défaut
    public HistoriqueBovinModel() {
    }

    // Getters et Setters
    public Integer getIdBovin() {
        return idBovin;
    }

    public void setIdBovin(Integer idBovin) {
        this.idBovin = idBovin;
    }

    public BigDecimal getPoidsAuMomentVente() {
        return poidsAuMomentVente;
    }

    public void setPoidsAuMomentVente(BigDecimal poidsAuMomentVente) {
        this.poidsAuMomentVente = poidsAuMomentVente;
    }

    public LocalDateTime getDateSortieVente() {
        return dateSortieVente;
    }

    public void setDateSortieVente(LocalDateTime dateSortieVente) {
        this.dateSortieVente = dateSortieVente;
    }
}