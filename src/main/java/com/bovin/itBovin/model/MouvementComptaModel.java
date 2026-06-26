package com.bovin.itBovin.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "mouvement_compta")
public class MouvementComptaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_mouvement")
    private MouvementModel mouvement;

    @ManyToOne
    @JoinColumn(name = "id_compte_compta")
    private CompteComptaModel compteCompta;

    private BigDecimal debit;
    private BigDecimal credit;
    // getters / setters
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
    public BigDecimal getDebit() {
        return debit;
    }
    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }
    public BigDecimal getCredit() {
        return credit;
    }
    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }
}