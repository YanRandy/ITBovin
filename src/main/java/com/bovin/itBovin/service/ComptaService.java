package com.bovin.itBovin.service;

import com.bovin.itBovin.model.*;
import com.bovin.itBovin.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ComptaService {

    private static final int TYPE_DEBIT = 1;
    private static final int TYPE_CREDIT = 2;
    private static final int COMPTE_CLIENT = 4;
    private static final int COMPTE_VENTES = 3;

    private final MouvementRepository mouvementRepository;
    private final MouvementComptaRepository mouvementComptaRepository;
    private final TypeMouvementRepository typeMouvementRepository;
    private final CompteComptaRepository compteComptaRepository;

    public ComptaService(MouvementRepository mouvementRepository,
                         MouvementComptaRepository mouvementComptaRepository,
                         TypeMouvementRepository typeMouvementRepository,
                         CompteComptaRepository compteComptaRepository) {
        this.mouvementRepository = mouvementRepository;
        this.mouvementComptaRepository = mouvementComptaRepository;
        this.typeMouvementRepository = typeMouvementRepository;
        this.compteComptaRepository = compteComptaRepository;
    }

    @Transactional
    public void enregistrerVenteCompta(Integer idVente, BigDecimal montantTotal, LocalDateTime dateSaisie) {
        // Récupération des références fixes
        TypeMouvementModel debitType = typeMouvementRepository.findById(TYPE_DEBIT)
                .orElseThrow(() -> new RuntimeException("Type DEBIT (1) introuvable"));
        TypeMouvementModel creditType = typeMouvementRepository.findById(TYPE_CREDIT)
                .orElseThrow(() -> new RuntimeException("Type CREDIT (2) introuvable"));
        CompteComptaModel compteClient = compteComptaRepository.findById(COMPTE_CLIENT)
                .orElseThrow(() -> new RuntimeException("Compte Client (4) introuvable"));
        CompteComptaModel compteVentes = compteComptaRepository.findById(COMPTE_VENTES)
                .orElseThrow(() -> new RuntimeException("Compte Ventes (3) introuvable"));

        // Mouvement DEBIT (client)
        MouvementModel mouvementDebit = new MouvementModel();
        mouvementDebit.setTypeMouvement(debitType);
        mouvementDebit.setIdVente(idVente);
        mouvementDebit.setDateMouvement(LocalDate.from(dateSaisie));
        mouvementDebit = mouvementRepository.save(mouvementDebit);

        MouvementComptaModel ecritureDebit = new MouvementComptaModel();
        ecritureDebit.setMouvement(mouvementDebit);
        ecritureDebit.setCompteCompta(compteClient);
        ecritureDebit.setDebit(montantTotal);
        ecritureDebit.setCredit(BigDecimal.ZERO);
        mouvementComptaRepository.save(ecritureDebit);

        // Mouvement CREDIT (ventes)
        MouvementModel mouvementCredit = new MouvementModel();
        mouvementCredit.setTypeMouvement(creditType);
        mouvementCredit.setIdVente(idVente);
        mouvementCredit.setDateMouvement(LocalDate.from(dateSaisie));
        mouvementCredit = mouvementRepository.save(mouvementCredit);

        MouvementComptaModel ecritureCredit = new MouvementComptaModel();
        ecritureCredit.setMouvement(mouvementCredit);
        ecritureCredit.setCompteCompta(compteVentes);
        ecritureCredit.setDebit(BigDecimal.ZERO);
        ecritureCredit.setCredit(montantTotal);
        mouvementComptaRepository.save(ecritureCredit);
    }
}