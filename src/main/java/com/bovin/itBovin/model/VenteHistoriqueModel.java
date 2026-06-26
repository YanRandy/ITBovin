package com.bovin.itBovin.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vente_historique")
public class VenteHistoriqueModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private ClientModel client;

    @Column(name = "date_saisie", nullable = false)
    private LocalDateTime dateSaisie = LocalDateTime.now();

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "montant_total_vente", nullable = false, precision = 12, scale = 2)
    private BigDecimal montantTotalVente = BigDecimal.ZERO;

    @OneToMany(mappedBy = "venteHistorique", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VenteHistoriqueDetailModel> details = new ArrayList<>();

    @OneToMany(mappedBy = "venteHistorique", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VenteDetailPaiementModel> paiements = new ArrayList<>();

    // Getters et setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public ClientModel getClient() { return client; }
    public void setClient(ClientModel client) { this.client = client; }

    public LocalDateTime getDateSaisie() { return dateSaisie; }
    public void setDateSaisie(LocalDateTime dateSaisie) { this.dateSaisie = dateSaisie; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getMontantTotalVente() { return montantTotalVente; }
    public void setMontantTotalVente(BigDecimal montantTotalVente) { this.montantTotalVente = montantTotalVente; }

    public List<VenteHistoriqueDetailModel> getDetails() { return details; }
    public void setDetails(List<VenteHistoriqueDetailModel> details) { this.details = details; }

    public List<VenteDetailPaiementModel> getPaiements() { return paiements; }
    public void setPaiements(List<VenteDetailPaiementModel> paiements) { this.paiements = paiements; }
}