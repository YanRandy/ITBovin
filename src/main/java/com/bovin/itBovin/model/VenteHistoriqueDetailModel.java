package com.bovin.itBovin.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vente_historique_detail")
public class VenteHistoriqueDetailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_vente_historique", nullable = false)
    private VenteHistoriqueModel venteHistorique;

    @ManyToOne
    @JoinColumn(name = "id_bovin", nullable = false)
    private BovinModel bovin;

    @Column(name = "prix_vente", nullable = false, precision = 12, scale = 2)
    private BigDecimal prixVente;

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public VenteHistoriqueModel getVenteHistorique() { return venteHistorique; }
    public void setVenteHistorique(VenteHistoriqueModel venteHistorique) { this.venteHistorique = venteHistorique; }

    public BovinModel getBovin() { return bovin; }
    public void setBovin(BovinModel bovin) { this.bovin = bovin; }

    public BigDecimal getPrixVente() { return prixVente; }
    public void setPrixVente(BigDecimal prixVente) { this.prixVente = prixVente; }
}