package com.bovin.itBovin.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "bovin")
public class BovinModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_race")
    private RaceModel race;
    @ManyToOne
    @JoinColumn(name = "id_lot")
    private LotModel lot;
    private Double poidsInitial;
    private Double poidsActuel;

    // postgres DATE -> java.sql.Date ? -> oui
    private Date dateNaissance;
    private Date dateArrivee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RaceModel getRace() {
        return race;
    }

    public void setRace(RaceModel race) {
        this.race = race;
    }

    public LotModel getLot() {
        return lot;
    }

    public void setLot(LotModel lot) {
        this.lot = lot;
    }

    public Double getPoidsInitial() {
        return poidsInitial;
    }

    public void setPoidsInitial(Double poidsInitial) {
        this.poidsInitial = poidsInitial;
    }

    public Double getPoidsActuel() {
        return poidsActuel;
    }

    public void setPoidsActuel(Double poidsActuel) {
        this.poidsActuel = poidsActuel;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

}
/*
 * id SERIAL PRIMARY KEY,
 * id_race INT REFERENCES race(id) ON DELETE RESTRICT,
 * id_lot INT REFERENCES lot(id) ON DELETE SET NULL,
 * poids_initial NUMERIC(6,2),
 * poids_actuel NUMERIC(6,2),
 * date_naissance DATE,
 * date_arrivee DATE DEFAULT CURRENT_DATE
 */

// model, controller, repository, service, et c'est quoi la classe pour les vues
// ? -> DTO PUTAINNNNNNN