package com.bovin.itBovin.dto;

public class ClientSearchCriteria {
    private String nom;
    private String adresse;
    private String contact;

    // Getters et setters (tous optionnels)
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}