-- Suppression des tables si elles existent (ordre respectant les clés étrangères)
DROP VIEW IF EXISTS v_dette_fournisseur;
DROP VIEW IF EXISTS v_achat_total;
DROP TABLE IF EXISTS mouvement_detail;
DROP TABLE IF EXISTS compte_compta;
DROP TABLE IF EXISTS mouvement;
DROP TABLE IF EXISTS type_mouvement;
DROP TABLE IF EXISTS achat_aliment_detail;
DROP TABLE IF EXISTS achat_bovin_detail;
DROP TABLE IF EXISTS achat;
DROP TABLE IF EXISTS aliment;
DROP TABLE IF EXISTS fournisseur;
DROP TABLE IF EXISTS bovin;
DROP TABLE IF EXISTS lot;
DROP TABLE IF EXISTS race;
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS role;

-- =========================================================================
-- Authentification
-- =========================================================================
CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    email VARCHAR(150) NOT NULL UNIQUE,
    motdepasse VARCHAR(255) NOT NULL,
    id_role INT REFERENCES role(id) ON DELETE RESTRICT
);

-- =========================================================================
-- Gestion de race & lot
-- =========================================================================
CREATE TABLE race (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE lot (
    id SERIAL PRIMARY KEY,
    id_race INT REFERENCES race(id) ON DELETE SET NULL,
    date_creation DATE DEFAULT CURRENT_DATE
);

-- =========================================================================
-- Gestion de bovin
-- =========================================================================
CREATE TABLE bovin (
    id SERIAL PRIMARY KEY,
    id_race INT REFERENCES race(id) ON DELETE RESTRICT,
    id_lot INT REFERENCES lot(id) ON DELETE SET NULL,
    poids_initial NUMERIC(6,2),
    poids_actuel NUMERIC(6,2),
    date_naissance DATE,
    date_arrivee DATE DEFAULT CURRENT_DATE
);

-- =========================================================================
-- Achat & Fournisseurs
-- =========================================================================
CREATE TABLE fournisseur (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(150) NOT NULL,
    contact VARCHAR(50),
    adresse TEXT
);

CREATE TABLE aliment (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    unite VARCHAR(20) NOT NULL -- ex: kg, sac, litre
);

CREATE TABLE achat (
    id SERIAL PRIMARY KEY,
    id_fournisseur INT REFERENCES fournisseur(id) ON DELETE RESTRICT,
    date_achat DATE NOT NULL DEFAULT CURRENT_DATE,
    description TEXT
);

CREATE TABLE achat_bovin_detail (
    id SERIAL PRIMARY KEY,
    id_achat INT REFERENCES achat(id) ON DELETE CASCADE,
    id_bovin INT REFERENCES bovin(id) ON DELETE RESTRICT,
    prix_achat NUMERIC(12,2) NOT NULL DEFAULT 0
);

CREATE TABLE achat_aliment_detail (
    id SERIAL PRIMARY KEY,
    id_achat INT REFERENCES achat(id) ON DELETE CASCADE,
    id_aliment INT REFERENCES aliment(id) ON DELETE RESTRICT,
    quantite NUMERIC(10,2) NOT NULL DEFAULT 0,
    prix_unitaire NUMERIC(12,2) NOT NULL DEFAULT 0,
    prix_total NUMERIC(12,2) GENERATED ALWAYS AS (quantite * prix_unitaire) STORED
);

-- =========================================================================
-- Comptabilité & Mouvements
-- =========================================================================
CREATE TABLE type_mouvement (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(50) NOT NULL UNIQUE
);

-- Note: id_vente n'a pas de table dédiée ici, le champ reste libre ou prêt pour évolution
CREATE TABLE mouvement (
    id SERIAL PRIMARY KEY,
    id_type_mouvement INT REFERENCES type_mouvement(id) ON DELETE RESTRICT,
    id_achat INT REFERENCES achat(id) ON DELETE SET NULL,
    id_vente INT, 
    date_mouvement DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE compte_compta (
    id SERIAL PRIMARY KEY,
    libelle VARCHAR(100) NOT NULL,
    numero VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE mouvement_detail (
    id SERIAL PRIMARY KEY,
    id_mouvement INT REFERENCES mouvement(id) ON DELETE CASCADE,
    id_compte_compta INT REFERENCES compte_compta(id) ON DELETE RESTRICT,
    debit NUMERIC(12,2) NOT NULL DEFAULT 0,
    credit NUMERIC(12,2) NOT NULL DEFAULT 0
);