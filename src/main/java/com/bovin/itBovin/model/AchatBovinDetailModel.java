package com.bovin.itBovin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "achat_bovin_detail")
public class AchatBovinDetailModel {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_achat")
    private AchatModel achat;

    @ManyToOne
    @JoinColumn(name = "id_bovin")
    private BovinModel bovin;

    @Column(name = "prix_achat")
    private Double prixAchat;

    public AchatBovinDetailModel() {
    }

    public AchatBovinDetailModel(AchatModel achat, BovinModel bovin, Double prixAchat) {
        this.achat = achat;
        this.bovin = bovin;
        this.prixAchat = prixAchat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AchatModel getAchat() {
        return achat;
    }

    public void setAchat(AchatModel achat) {
        this.achat = achat;
    }

    public BovinModel getBovin() {
        return bovin;
    }

    public void setBovin(BovinModel bovin) {
        this.bovin = bovin;
    }

    public Double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }
    
}
