package com.bovin.itBovin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bovin.itBovin.model.MouvementDetailModel;
import com.bovin.itBovin.repository.MouvementDetailRepository;

@Service
public class MouvementDetailService {
    
    @Autowired
    private MouvementDetailRepository mouvementDetailRepository;
    
    public List<MouvementDetailModel> findDetailByMouvementAndCompte(Long idMouvement, Long idCompteCompta) {
        return mouvementDetailRepository.findDetailByMouvementAndCompte(idMouvement, idCompteCompta);
    }
    
    public MouvementDetailModel findDetailByMouvementAndCompteSingle(Long idMouvement, Long idCompteCompta) {
        return mouvementDetailRepository.findDetailByMouvementAndCompteSingle(idMouvement, idCompteCompta);
    }
    
    public void updateDebit(Long id, Double nouveauDebit) {
        mouvementDetailRepository.updateDebit(id, nouveauDebit);
    }
    
    public void updateCredit(Long id, Double nouveauCredit) {
        mouvementDetailRepository.updateCredit(id, nouveauCredit);
    }
}