package com.bovin.itBovin.err;

public class ClientNotFoundErr extends Exception{
    int id;

    public ClientNotFoundErr(int id) {
        super("Client introuvable avec l'id : " + id);
        this.id = id;
    }
}
