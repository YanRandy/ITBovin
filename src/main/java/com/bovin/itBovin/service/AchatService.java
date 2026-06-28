package com.bovin.itBovin.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bovin.itBovin.dto.AchatBovinDto;
import com.bovin.itBovin.model.AchatAlimentDetail;
import com.bovin.itBovin.model.AchatBovinDetailModel;
import com.bovin.itBovin.model.AchatModel;
import com.bovin.itBovin.model.AlimentModel;
import com.bovin.itBovin.model.BovinModel;
import com.bovin.itBovin.model.CompteComptaModel;
import com.bovin.itBovin.model.FournisseurModel;
import com.bovin.itBovin.model.MouvementDetailModel;
import com.bovin.itBovin.model.MouvementModel;
import com.bovin.itBovin.model.RaceModel;
import com.bovin.itBovin.model.TypeMouvementModel;
import com.bovin.itBovin.repository.AchatAlimentDetailRepository;
import com.bovin.itBovin.repository.AchatBovinDetailRepository;
import com.bovin.itBovin.repository.AchatRepository;
import com.bovin.itBovin.repository.BovinRepository;
import com.bovin.itBovin.repository.CompteComptaRepository;
import com.bovin.itBovin.repository.FournisseurRepository;
import com.bovin.itBovin.repository.MouvementDetailRepository;
import com.bovin.itBovin.repository.MouvementRepository;
import com.bovin.itBovin.repository.RaceRepository;
import com.bovin.itBovin.repository.TypeMouvementRepository;

import jakarta.transaction.Transactional;

@Service
public class AchatService {

    private final int NUMERO_COMPTE_ACHAT = 601;
    private final int NUMERO_COMPTE_FOURNISSEUR = 401;
    @Autowired
    private BovinRepository bovinRepository;

    @Autowired
    private AchatRepository achatRepository;

    @Autowired
    private AchatBovinDetailRepository achatBovinDetailRepository;

    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private MouvementDetailRepository mouvementDetailRepository;

    @Autowired
    private TypeMouvementRepository typeMouvementRepository;

    @Autowired
    private CompteComptaRepository compteComptaRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private  AchatAlimentDetailRepository achatAlimentDetailRepository;

    @Transactional
    public void acheterBovin(AchatBovinDto achatBovinDto) {

        // recuperer le fournisseur
        Optional<FournisseurModel> optionalFournisseur = this.fournisseurRepository
                .findById(achatBovinDto.idFournisseur());
        if (optionalFournisseur.isEmpty()) {
            throw new RuntimeException("Fournisseur id introuvable : '" + achatBovinDto.idFournisseur() + "'");
        }

        // recuperer la race
        Optional<RaceModel> optionalRace = this.raceRepository.findById(achatBovinDto.idRace());
        if (optionalRace.isEmpty()) {
            throw new RuntimeException("Race id introuvable : '" + achatBovinDto.idRace() + "'");
        }

        // save achat
        AchatModel achat = new AchatModel(optionalFournisseur.get(), achatBovinDto.dateArrive(), "");
        achatRepository.save(achat);

        BovinModel bovin = new BovinModel(optionalRace.get(), achatBovinDto.poidInitial(),
                achatBovinDto.dateNaissance(), achatBovinDto.dateArrive());

        // save bovin
        bovinRepository.save(bovin);

        // save bovin detail
        AchatBovinDetailModel achatBovinDetail = new AchatBovinDetailModel(achat, bovin, achatBovinDto.prixAchat());
        achatBovinDetailRepository.save(achatBovinDetail);

        // save mouvement
        Optional<TypeMouvementModel> optionalTypeMvtAchat = this.typeMouvementRepository.findByLibelle("achat");

        if (optionalTypeMvtAchat.isEmpty()) {
            throw new RuntimeException("Type  mouvement 'achat' introuvable dans la base");
        }
        MouvementModel mouvement = new MouvementModel(optionalTypeMvtAchat.get(), achatBovinDto.dateArrive());
        mouvement.setAchat(achat);
        mouvementRepository.save(mouvement);

        manageComptaOnAchat(achatBovinDto.prixAchat(), mouvement);
    }

    /*
     * -- compte 6 : débiter du montant total de l'achat
     * -- compte 40 : créditer du montant total de l'achat
     */
    private void manageComptaOnAchat(Double prixAchat, MouvementModel mouvement) {
        // recuperer les compte
        Optional<CompteComptaModel> compteAchat = compteComptaRepository.findByNumero(this.NUMERO_COMPTE_ACHAT);

        Optional<CompteComptaModel> compteFournisseur = compteComptaRepository
                .findByNumero(this.NUMERO_COMPTE_FOURNISSEUR);

        if (compteAchat.isEmpty()) {
            throw new RuntimeException(
                    "Compte compta : 'achat' introuvable .\n Numero rechercher : '" + this.NUMERO_COMPTE_ACHAT + "'");
        }

        if (compteFournisseur.isEmpty()) {
            throw new RuntimeException("Compte compta : 'fournisseur' introuvable .\n Numero rechercher : '"
                    + this.NUMERO_COMPTE_FOURNISSEUR + "'");
        }

        // effectuer le debit sur le compte achat
        MouvementDetailModel debit = new MouvementDetailModel(mouvement, compteAchat.get());
        debit.setDebit(prixAchat);

        // effecuter le credit sur le compte fournisseur
        MouvementDetailModel credit = new MouvementDetailModel(mouvement, compteFournisseur.get());
        credit.setCredit(prixAchat);

        this.mouvementDetailRepository.save(debit);
        this.mouvementDetailRepository.save(credit);
    }
    @Transactional
    public void achatAliment(AlimentModel aliment,
                             FournisseurModel fournisseur,
                             Double quantite,
                             Double prixUnitaire) {

        // =========================
        // 1. CREATE ACHAT
        // =========================
        AchatModel achat = new AchatModel();
        achat.setFournisseur(fournisseur);
       

achat.setDateAchat(new Date(System.currentTimeMillis()));

        achat = achatRepository.save(achat);

        // =========================
        // 2. CREATE DETAIL ACHAT
        // =========================
        

        AchatAlimentDetail detail = new AchatAlimentDetail();
        detail.setAchat(achat);
        detail.setAliment(aliment);
        detail.setQuantite(BigDecimal.valueOf(quantite));
        detail.setPrixUnitaire(BigDecimal.valueOf(prixUnitaire));

        achatAlimentDetailRepository.save(detail);

        // total
        double total = quantite * prixUnitaire;

        // =========================
        // 3. CREATE MOUVEMENT
        // =========================
        TypeMouvementModel type = typeMouvementRepository.findByLibelle("achat")
        .orElseThrow(() -> new RuntimeException("type mouvement achat introuvable"));

        MouvementModel mouvement = new MouvementModel();
        mouvement.setTypeMouvement(type);
        mouvement.setAchat(achat);
        mouvement.setDateMouvement(new Date(System.currentTimeMillis()));



        mouvement = mouvementRepository.save(mouvement);

        // =========================
        // 4. COMPTABILITÉ
        // =========================

        CompteComptaModel compteAchat = compteComptaRepository
    .findByNumero(NUMERO_COMPTE_ACHAT)
    .orElseThrow(() -> new RuntimeException("Compte 601 introuvable"));

CompteComptaModel compteFournisseur = compteComptaRepository
    .findByNumero(NUMERO_COMPTE_FOURNISSEUR)
    .orElseThrow(() -> new RuntimeException("Compte 401 introuvable"));

        // débit (charges)
        MouvementDetailModel debit = new MouvementDetailModel();
        debit.setMouvement(mouvement);
        debit.setCompteCompta(compteAchat);
        debit.setDebit(total);
        debit.setCredit(0.0);

        // crédit (dette fournisseur)
        MouvementDetailModel credit = new MouvementDetailModel();
        credit.setMouvement(mouvement);
        credit.setCompteCompta(compteFournisseur);
        credit.setDebit(0.0);
        credit.setCredit(total);

        mouvementDetailRepository.save(debit);
        mouvementDetailRepository.save(credit);
    }

}
