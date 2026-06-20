package com.bovin.itBovin.dto;

public record BovinPayloadDto(
        Integer idLot,
        Integer idRace,
        Double poidsInitial,
        Double poidsActuelle,
        String dateNaissance,
        String dateArrivee,
        Integer quantite) {
}