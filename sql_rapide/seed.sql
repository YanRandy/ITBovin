INSERT INTO role (libelle) VALUES ('admin'), ('membre');

INSERT INTO "user" (nom, prenom, email, motdepasse, id_role) VALUES
('Ranaivo', 'Jean', 'jean.ranaivo@example.com', 'hash_password_123', 1),
('Rakoto', 'Paul', 'paul.rakoto@example.com', 'hash_password_456', 2);

INSERT INTO race (libelle) VALUES ('Zébu de Madagascar'), ('Charolaise'), ('Limousine');

INSERT INTO lot (id_race, date_creation) VALUES 
(1, '2026-01-15'), 
(2, '2026-02-20');

INSERT INTO bovin (id_race, id_lot, poids_initial, poids_actuel, date_naissance, date_arrivee) VALUES
(1, 1, 150.00, 180.00, '2025-05-10', '2026-01-15'),
(1, 1, 140.00, 175.00, '2025-06-01', '2026-01-15'),
(2, 2, 200.00, 210.00, '2025-04-12', '2026-02-20');

INSERT INTO fournisseur (nom, contact, adresse) VALUES 
('AGRI Madagascar', '+261 34 00 000 01', 'Ankorondrano, Antananarivo'),
('Élevage Pro', '+261 32 00 000 02', 'Antsirabe');

INSERT INTO aliment (libelle, unite) VALUES 
('Provende Croissance', 'kg'),
('Sons de riz', 'sac');

INSERT INTO achat (id_fournisseur, date_achat, description) VALUES
(1, '2026-01-15', 'Achat initial de 2 zébus et provende'),
(2, '2026-02-20', 'Achat d''un bovin Charolais et sacs de son');

INSERT INTO achat_bovin_detail (id_achat, id_bovin, prix_achat) VALUES
(1, 1, 1200000.00),
(1, 2, 1150000.00),
(2, 3, 2500000.00);

INSERT INTO achat_aliment_detail (id_achat, id_aliment, quantite, prix_unitaire) VALUES
(1, 1, 100, 2500.00),
(2, 2, 10, 45000.00);

INSERT INTO type_mouvement (libelle) VALUES ('achat'), ('vente');

INSERT INTO compte_compta (id, libelle, numero) VALUES
(1, 'Achat - 6', '601'),
(2, 'Fournisseurs - 40', '401'),
(3, 'Ventes - 7', '701'),
(4, 'Clients - 41', '411'),
(5, 'Caisse - 57', '571'),
(6, 'Banque - 51', '512');

INSERT INTO mouvement (id_type_mouvement, id_achat, date_mouvement) VALUES (1, 1, '2026-01-15');
INSERT INTO mouvement_detail (id_mouvement, id_compte_compta, debit, credit) VALUES
(1, 1, 2600000.00, 0.00),
(1, 2, 0.00, 2600000.00);

INSERT INTO mouvement (id_type_mouvement, id_achat, date_mouvement) VALUES (1, 1, '2026-01-16');
INSERT INTO mouvement_detail (id_mouvement, id_compte_compta, debit, credit) VALUES
(2, 2, 2000000.00, 0.00),
(2, 6, 0.00, 2000000.00);

INSERT INTO mouvement (id_type_mouvement, id_achat, date_mouvement) VALUES (1, 2, '2026-02-20');
INSERT INTO mouvement_detail (id_mouvement, id_compte_compta, debit, credit) VALUES
(3, 1, 2950000.00, 0.00),
(3, 2, 0.00, 2950000.00);