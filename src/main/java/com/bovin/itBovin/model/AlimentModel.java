package com.bovin.itBovin.model;



import jakarta.persistence.*;

@Entity
@Table(name = "aliment")
public class AlimentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String libelle;

    @Column(nullable = false, length = 20)
    private String unite;

    public AlimentModel() {
    }

    public AlimentModel(Long id, String libelle, String unite) {
        this.id = id;
        this.libelle = libelle;
        this.unite = unite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }
}