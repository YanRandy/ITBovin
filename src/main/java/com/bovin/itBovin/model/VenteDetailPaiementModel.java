package com.bovin.itBovin.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vente_detail_paiement")
public class VenteDetailPaiementModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_vente_historique", nullable = false)
    private VenteHistoriqueModel venteHistorique;

    @Column(nullable = false, length = 255)
    private String libelle;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montant;

    @Column(name = "date_paiement", nullable = false)
    private LocalDateTime datePaiement = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "id_caisse", nullable = false)
    private CaisseModel caisse;

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public VenteHistoriqueModel getVenteHistorique() { return venteHistorique; }
    public void setVenteHistorique(VenteHistoriqueModel venteHistorique) { this.venteHistorique = venteHistorique; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public BigDecimal getMontant() { return montant; }
    public void setMontant(BigDecimal montant) { this.montant = montant; }

    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }

    public CaisseModel getCaisse() { return caisse; }
    public void setCaisse(CaisseModel caisse) { this.caisse = caisse; }
}