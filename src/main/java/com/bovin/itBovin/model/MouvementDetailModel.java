package com.bovin.itBovin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "compte_compta")
public class MouvementDetailModel {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_mouvement")
    private MouvementModel mouvement;

    @Column(name = "id_compte_compta")
    private CompteComptaModel compteCompta;

    @Column(name = "debit")
    private Double debit = 0.;

    @Column(name = "credit")
    private Double credit = 0.;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MouvementModel getMouvement() {
        return mouvement;
    }

    public void setMouvement(MouvementModel mouvement) {
        this.mouvement = mouvement;
    }

    public CompteComptaModel getCompteCompta() {
        return compteCompta;
    }

    public void setCompteCompta(CompteComptaModel compteCompta) {
        this.compteCompta = compteCompta;
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
