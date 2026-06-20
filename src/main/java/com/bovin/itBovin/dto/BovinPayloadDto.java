package com.bovin.itBovin.dto;

public class BovinPayloadDto {
        private Integer idLot;
        private Integer idRace;
        private Double poidsInit;
        private Double poidsActuelle;
        private String dateInit;
        private String dateArrivee;
        private Integer quantite;

        public Integer getIdLot() {
                return idLot;
        }

        public void setIdLot(Integer idLot) {
                this.idLot = idLot;
        }

        public Integer getIdRace() {
                return idRace;
        }

        public void setIdRace(Integer idRace) {
                this.idRace = idRace;
        }

        public Double getPoidsInit() {
                return poidsInit;
        }

        public void setPoidsInit(Double poidsInit) {
                this.poidsInit = poidsInit;
        }

        public Double getPoidsActuelle() {
                return poidsActuelle;
        }

        public void setPoidsActuelle(Double poidsActuelle) {
                this.poidsActuelle = poidsActuelle;
        }

        public String getDateInit() {
                return dateInit;
        }

        public void setDateInit(String dateInit) {
                this.dateInit = dateInit;
        }

        public String getDateArrivee() {
                return dateArrivee;
        }

        public void setDateArrivee(String dateArrivee) {
                this.dateArrivee = dateArrivee;
        }

        public Integer getQuantite() {
                return quantite;
        }

        public void setQuantite(Integer quantite) {
                this.quantite = quantite;
        }

}