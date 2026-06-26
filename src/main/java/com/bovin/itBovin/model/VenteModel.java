package com.bovin.itBovin.model;

import java.sql.Timestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "vente")
public class VenteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_client", nullable = false)
    private Integer idClient;

    @Column(name = "id_bovin", nullable = false)
    private Integer idBovin;

    @Column(name = "date_vente", nullable = false)
    private Timestamp dateVente;

    @Column(name = "description")
    private String description;

    public VenteModel() {
    }

    // Getters & Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Integer getIdBovin() {
        return idBovin;
    }

    public void setIdBovin(Integer idBovin) {
        this.idBovin = idBovin;
    }

    public Timestamp getDateVente() {
        return dateVente;
    }

    public void setDateVente(Timestamp dateVente) {
        this.dateVente = dateVente;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}