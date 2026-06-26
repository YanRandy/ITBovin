package com.bovin.itBovin.model;

import jakarta.persistence.*;

@Entity
@Table(name = "compte_compta")
public class CompteComptaModel {
    @Id
    private Integer id;  // ID fixe (ex: 3,4)
    private String libelle;
    private String numero;
    // getters / setters
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
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
}