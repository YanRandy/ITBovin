package com.bovin.itBovin.model;

public class MouvementDetailModel {
    
    private Integer id;
    private Integer idMouvement;
    private Integer idCompteCompta;
    private Double debit;
    private Double credit;
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getIdMouvement() {
        return idMouvement;
    }
    
    public void setIdMouvement(Integer idMouvement) {
        this.idMouvement = idMouvement;
    }
    
    public Integer getIdCompteCompta() {
        return idCompteCompta;
    }
    
    public void setIdCompteCompta(Integer idCompteCompta) {
        this.idCompteCompta = idCompteCompta;
    }
    
    public Double getDebit() {
        return debit;
    }
    
    public void setDebit(Double debit) {
        this.debit = debit;
    }
    
    public Double getCredit() {
        return credit;
    }
    
    public void setCredit(Double credit) {
        this.credit = credit;
    }
}