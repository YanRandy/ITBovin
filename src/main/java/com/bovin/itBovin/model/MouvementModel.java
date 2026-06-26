package com.bovin.itBovin.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "mouvement")
public class MouvementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_type_mouvement")
    private TypeMouvementModel typeMouvement;

    @Column(name = "id_achat")
    private Integer idAchat;

    @Column(name = "id_vente")
    private Integer idVente;

    @Column(name = "date_mouvement")
    private LocalDate dateMouvement;
    // getters / setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TypeMouvementModel getTypeMouvement() {
        return typeMouvement;
    }

    public void setTypeMouvement(TypeMouvementModel typeMouvement) {
        this.typeMouvement = typeMouvement;
    }

    public Integer getIdAchat() {
        return idAchat;
    }

    public void setIdAchat(Integer idAchat) {
        this.idAchat = idAchat;
    }

    public Integer getIdVente() {
        return idVente;
    }

    public void setIdVente(Integer idVente) {
        this.idVente = idVente;
    }

    public LocalDate getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(LocalDate dateMouvement) {
        this.dateMouvement = dateMouvement;
    }
}