package com.bovin.itBovin.dto;

import java.sql.Date;

public record AchatBovinDto(
        Integer idFournisseur,
        Integer idRace,
        Double poidInitial,
        Date dateNaissance,
        Date dateArrive,
        Double prixAchat) {

}
