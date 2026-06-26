-- Roles & Users
INSERT INTO role (libelle) VALUES ('admin'), ('membre');
INSERT INTO "user" (nom, prenom, email, motdepasse, id_role) VALUES
('Ranaivo', 'Jean', 'jean.ranaivo@example.com', 'hash_password_123', 1),
('Rakoto', 'Paul', 'paul.rakoto@example.com', 'hash_password_456', 2);

-- Races & Lots
INSERT INTO race (libelle) VALUES ('Zébu de Madagascar'), ('Charolaise'), ('Limousine');
INSERT INTO lot (id_race, date_creation) VALUES 
(1, '2026-01-15'), 
(2, '2026-02-20');

-- Bovins (Insérés avant l'achat pour pouvoir lier les ID dans les détails d'achat)
INSERT INTO bovin (id_race, id_lot, poids_initial, poids_actuel, date_naissance, date_arrivee) VALUES
(1, 1, 150.00, 180.00, '2025-05-10', '2026-01-15'),
(1, 1, 140.00, 175.00, '2025-06-01', '2026-01-15'),
(2, 2, 200.00, 210.00, '2025-04-12', '2026-02-20');

-- Fournisseurs & Aliments
INSERT INTO fournisseur (nom, contact, adresse) VALUES 
('AGRI Madagascar', '+261 34 00 000 01', 'Ankorondrano, Antananarivo'),
('Élevage Pro', '+261 32 00 000 02', 'Antsirabe');

INSERT INTO aliment (libelle, unite) VALUES 
('Provende Croissance', 'kg'),
('Sons de riz', 'sac');

-- Achats
INSERT INTO achat (id_fournisseur, date_achat, description) VALUES
(1, '2026-01-15', 'Achat initial de 2 zébus et provende'),
(2, '2026-02-20', 'Achat d''un bovin Charolais et sacs de son');

-- Détails des Achats
INSERT INTO achat_bovin_detail (id_achat, id_bovin, prix_achat) VALUES
(1, 1, 1200000.00),
(1, 2, 1150000.00),
(2, 3, 2500000.00);

INSERT INTO achat_aliment_detail (id_achat, id_aliment, quantite, prix_unitaire) VALUES
(1, 1, 100, 2500.00), -- Total: 250 000
(2, 2, 10, 45000.00);  -- Total: 450 000

-- Comptabilité Setup
INSERT INTO type_mouvement (libelle) VALUES ('DEBIT'), ('CREDIT');

-- IMPORTANT : On force les ID ici pour correspondre aux filtres (ex: Fournisseurs = ID 2)
INSERT INTO compte_compta (id, libelle, numero) VALUES
(1, 'Achat - 6', '601'),
(2, 'Fournisseurs - 40', '401'),
(3, 'Ventes - 7', '701'),
(4, 'Clients - 41', '411'),
(5, 'Caisse - 57', '571'),
(6, 'Banque - 51', '512');

-- Mouvements & Écritures Comptables
-- Achat 1 (Total = 1 200 000 + 1 150 000 + 250 000 = 2 600 000)
INSERT INTO mouvement (id_type_mouvement, id_achat, date_mouvement) VALUES (1, 1, '2026-01-15');
INSERT INTO mouvement_compta (id_mouvement, id_compte_compta, debit, credit) VALUES
(1, 1, 2600000.00, 0.00),       -- Débit du compte Achat
(1, 2, 0.00, 2600000.00);       -- Crédit du compte Fournisseur (Dette créée)

-- Paiement Partiel pour l'Achat 1 (On diminue la dette chez le fournisseur de 2 000 000)
INSERT INTO mouvement (id_type_mouvement, id_achat, date_mouvement) VALUES (1, 1, '2026-01-16');
INSERT INTO mouvement_compta (id_mouvement, id_compte_compta, debit, credit) VALUES
(2, 2, 2000000.00, 0.00),       -- Débit Fournisseur (diminution dette)
(2, 6, 0.00, 2000000.00);       -- Crédit Banque

-- Achat 2 (Total = 2 500 000 + 450 000 = 2 950 000) -> Non payé du tout
INSERT INTO mouvement (id_type_mouvement, id_achat, date_mouvement) VALUES (1, 2, '2026-02-20');
INSERT INTO mouvement_compta (id_mouvement, id_compte_compta, debit, credit) VALUES
(3, 1, 2950000.00, 0.00),
(3, 2, 0.00, 2950000.00);



INSERT INTO client (nom, adresse, contact) VALUES
('Jean Rakoto', 'Lot II A 123 Analakely, Antananarivo', '034 12 345 67'),
('Marie Rasoa', 'Lot III B 45 Anosy, Antananarivo', '033 45 678 90'),
('Paul Andrianina', 'Ambohijatovo, Antananarivo', '032 11 223 34'),
('Lucie Raveloson', 'Antsirabe', '034 98 765 43'),
('Hery Rakotondrazaka', 'Toamasina', '032 56 789 01'),
('Fanja Randriamihaja', 'Mahajanga', '033 22 334 45'),
('Nirina Razafindrakoto', 'Fianarantsoa', '034 66 778 89'),
('Mamy Rasoanaivo', 'Toliara', '032 90 123 45'),
('Sarah Ramananarivo', 'Antsiranana', '033 55 667 78'),
('Patrick Rakotomalala', 'Morondava', '034 44 556 67');