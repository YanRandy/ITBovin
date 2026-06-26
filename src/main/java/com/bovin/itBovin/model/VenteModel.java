package com.bovin.itBovin.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "vente")
public class VenteModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_client", nullable = false)
    private ClientModel client;

    @Column(name = "date_vente", nullable = false)
    private LocalDateTime dateVente = LocalDateTime.now();

    private String description;

    // Seule relation vers les bovins vendus
    @OneToMany(mappedBy = "vente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VenteBovinDetailModel> bovinsVendus;

    // Constructeurs, getters et setters
    public VenteModel() {}

    // Getters/Setters...
}