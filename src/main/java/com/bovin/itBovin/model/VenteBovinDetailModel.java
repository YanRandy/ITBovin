package com.bovin.itBovin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vente_bovin_detail",
       uniqueConstraints = @UniqueConstraint(columnNames = {"id_vente", "id_bovin"}))
public class VenteBovinDetailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_vente", nullable = false)
    private VenteModel vente;

    @ManyToOne
    @JoinColumn(name = "id_bovin", nullable = false)
    private BovinModel bovin;

    @Column(name = "prix_vente", nullable = false)
    private Double prixVente;

    // Constructeurs, getters et setters
    public VenteBovinDetailModel() {}

    // Getters/Setters...
}