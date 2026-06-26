package com.bovin.itBovin.dto;

public class BovinDTO {
    private Integer id;
    private String raceLibelle;
    private Double poidsActuel;
    private Integer lotId;
    // constructeurs, getters, setters
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getRaceLibelle() {
        return raceLibelle;
    }
    public void setRaceLibelle(String raceLibelle) {
        this.raceLibelle = raceLibelle;
    }
    public Double getPoidsActuel() {
        return poidsActuel;
    }
    public void setPoidsActuel(Double poidsActuel) {
        this.poidsActuel = poidsActuel;
    }
    public Integer getLotId() {
        return lotId;
    }
    public void setLotId(Integer lotId) {
        this.lotId = lotId;
    }
}