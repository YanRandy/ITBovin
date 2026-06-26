-- =========================================================================
-- Structure pour l'Historique des Ventes (Multi-bovins & Lots)
-- =========================================================================

CREATE TABLE vente_historique (
    id SERIAL PRIMARY KEY,
    id_client INT NOT NULL REFERENCES client(id) ON DELETE RESTRICT,
    date_saisie TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    montant_total_vente NUMERIC(12,2) NOT NULL DEFAULT 0
);

-- Table de détails de l'historique (permet l'insertion multiple de bovins par vente)
CREATE TABLE vente_historique_detail (
    id SERIAL PRIMARY KEY,
    id_vente_historique INT REFERENCES vente_historique(id) ON DELETE CASCADE,
    id_bovin INT NOT NULL REFERENCES bovin(id) ON DELETE RESTRICT,
    prix_vente NUMERIC(12,2) NOT NULL
);

-- ============================
-- Table : vente_detail_paiement
-- ============================
CREATE TABLE vente_detail_paiement (
    id SERIAL PRIMARY KEY,

    id_vente_historique INTEGER NOT NULL,

    libelle VARCHAR(255) NOT NULL,
    mode_paiement VARCHAR(50) NOT NULL,
    montant NUMERIC(12,2) NOT NULL CHECK (montant > 0),
    date_paiement TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_vente_detail_paiement_vente
        FOREIGN KEY (id_vente_historique)
        REFERENCES vente_historique(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

-- Table historique_bovin pour archiver les bovins sortis des lots actifs
CREATE TABLE historique_bovin (
    id INT PRIMARY KEY, -- Conserve le même ID que la table bovin d'origine
    id_race INT,
    poids_au_moment_vente NUMERIC(6,2),
    date_naissance DATE,
    date_arrivee DATE,
    date_sortie_vente TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);