package com.bovin.itBovin.model;

import java.sql.Date;

public class MouvementModel {
    
    private Integer id;
    private Integer idTypeMouvement;
    private Integer idAchat;
    private Integer idVente;
    private Date dateMouvement;
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getIdTypeMouvement() {
        return idTypeMouvement;
    }
    
    public void setIdTypeMouvement(Integer idTypeMouvement) {
        this.idTypeMouvement = idTypeMouvement;
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
    
    public Date getDateMouvement() {
        return dateMouvement;
    }
    
    public void setDateMouvement(Date dateMouvement) {
        this.dateMouvement = dateMouvement;
    }
}