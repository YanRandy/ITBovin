package com.bovin.itBovin.err;

public class BovinNotFoundErr extends Exception {
    public BovinNotFoundErr(String message) {
        super(message);
    }

    public BovinNotFoundErr(Integer id) {
        super("Bovin avec l'ID " + id + " non trouvé.");
    }
}