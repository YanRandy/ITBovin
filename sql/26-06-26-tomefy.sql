CREATE TABLE caisse (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    solde_initial NUMERIC(12,2) DEFAULT 0,
    solde_actuelle NUMERIC(12,2) DEFAULT 0
);

ALTER TABLE mouvement
ADD COLUMN id_caisse INT REFERENCES caisse(id);