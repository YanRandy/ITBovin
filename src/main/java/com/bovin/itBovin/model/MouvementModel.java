package com.bovin.itBovin.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "mouvement")
public class MouvementModel {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_type_mouvement")
    private TypeMouvementModel typeMouvement;

    @ManyToOne
    @JoinColumn(name = "id_achat")
    private AchatModel achat;

    @Column(name = "id_vente")
    private Integer idVente = 0;

    @Column(name = "date_mouvement")
    private Date dateMouvement;

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

    public AchatModel getAchat() {
        return achat;
    }

    public void setAchat(AchatModel achat) {
        this.achat = achat;
    }

    public Integer getIdVente() {
        return idVente;
    }

    public void setIdVente(Integer idVente) {
        this.idVente = idVente;
    }

    public Date getDateMouvement() {
        return dateMouvement;
    }

    public void setDateMouvement(Date dateMouvement) {
        this.dateMouvement = dateMouvement;
    }
}
