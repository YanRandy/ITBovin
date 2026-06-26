-- =============================================
-- SCRIPT DE TEST POUR LES DETTES FOURNISSEURS
-- À exécuter dans PostgreSQL
-- Base de données: itBovin (ou itBovin)
-- =============================================

-- 1. VÉRIFIER LES TABLES NÉCESSAIRES
-- =============================================

-- Vérifier que les tables existent
SELECT table_name 
FROM information_schema.tables 
WHERE table_schema = 'public' 
AND table_name IN ('fournisseur', 'race', 'lot', 'bovin', 'achat', 'achat_bovin_detail', 
                   'mouvement', 'mouvement_detail', 'compte_compta', 'type_mouvement');


-- 2. INSÉRER LES DONNÉES DE BASE (si nécessaire)
-- =============================================

-- Fournisseurs
INSERT INTO fournisseur (nom, contact, adresse) VALUES 
    ('AGRI Madagascar', '+261 34 00 000 01', 'Ankorondrano, Antananarivo'),
    ('Élevage Pro', '+261 32 00 000 02', 'Antsirabe'),
    ('Bovin Export SARL', '+261 33 00 000 03', 'Toamasina')
ON CONFLICT (nom) DO NOTHING;

-- Races
INSERT INTO race (libelle) VALUES 
    ('Zébu de Madagascar'),
    ('Charolaise'),
    ('Limousine')
ON CONFLICT (libelle) DO NOTHING;

-- Lots
INSERT INTO lot (id_race, date_creation) VALUES 
    (1, '2026-01-15'),
    (2, '2026-02-20'),
    (1, '2026-06-01')
ON CONFLICT DO NOTHING;

-- Bovins
INSERT INTO bovin (id_race, id_lot, poids_initial, poids_actuel, date_naissance, date_arrivee) VALUES
    (1, 1, 150.00, 180.00, '2025-05-10', '2026-01-15'),
    (1, 1, 140.00, 175.00, '2025-06-01', '2026-01-15'),
    (2, 2, 200.00, 210.00, '2025-04-12', '2026-02-20'),
    (1, 3, 120.00, 145.00, '2025-07-15', '2026-06-01'),
    (1, 3, 130.00, 155.00, '2025-08-01', '2026-06-01')
ON CONFLICT DO NOTHING;

-- Comptes comptables
INSERT INTO compte_compta (id, libelle, numero) VALUES
    (1, 'Achat - 6', '601'),
    (2, 'Fournisseurs - 40', '401'),
    (5, 'Caisse - 57', '571'),
    (6, 'Banque - 51', '512')
ON CONFLICT (id) DO UPDATE SET libelle = EXCLUDED.libelle;

-- Types de mouvement
INSERT INTO type_mouvement (libelle) VALUES ('achat'), ('vente')
ON CONFLICT (libelle) DO NOTHING;


-- 3. CRÉER LES ACHATS AVEC DETTES
-- =============================================

-- Supprimer les anciennes données de test (optionnel)
DELETE FROM mouvement_detail WHERE id_mouvement IN (SELECT id FROM mouvement WHERE id_achat IN (1, 2, 3, 4));
DELETE FROM mouvement WHERE id_achat IN (1, 2, 3, 4);
DELETE FROM achat_bovin_detail WHERE id_achat IN (1, 2, 3, 4);
DELETE FROM achat WHERE id IN (1, 2, 3, 4);
-- Réinitialiser les séquences (optionnel)
-- SELECT setval('achat_id_seq', 1, false);


-- 3.1 ACHAT 1 : Dette de 250,000 Ar (non payée)
INSERT INTO achat (id, id_fournisseur, date_achat, description) VALUES 
    (1, 1, '2026-06-01', 'Achat de provende - Dette 250,000 Ar');
    
INSERT INTO achat_aliment_detail (id_achat, id_aliment, quantite, prix_unitaire) VALUES
    (1, 1, 100, 2500.00);

-- Mouvement d'achat
INSERT INTO mouvement (id, id_type_mouvement, id_achat, date_mouvement) VALUES 
    (1, 1, 1, '2026-06-01');

-- Détails comptables (création de la dette)
INSERT INTO mouvement_detail (id_mouvement, id_compte_compta, debit, credit) VALUES
    (1, 1, 250000.00, 0.00),   -- Débit compte achat
    (1, 2, 0.00, 250000.00);    -- Crédit fournisseur = DETTE


-- 3.2 ACHAT 2 : Dette de 450,000 Ar (payée partiellement)
INSERT INTO achat (id, id_fournisseur, date_achat, description) VALUES 
    (2, 2, '2026-06-10', 'Achat de bovin - Dette 450,000 Ar - Payé 200,000 Ar');
    
INSERT INTO bovin (id_race, id_lot, poids_initial, poids_actuel, date_naissance, date_arrivee) VALUES
    (2, 2, 220.00, 235.00, '2025-09-10', '2026-06-10');

INSERT INTO achat_bovin_detail (id_achat, id_bovin, prix_achat) VALUES
    (2, (SELECT MAX(id) FROM bovin), 450000.00);

-- Mouvement d'achat (dette)
INSERT INTO mouvement (id, id_type_mouvement, id_achat, date_mouvement) VALUES 
    (2, 1, 2, '2026-06-10');

INSERT INTO mouvement_detail (id_mouvement, id_compte_compta, debit, credit) VALUES
    (2, 1, 450000.00, 0.00),
    (2, 2, 0.00, 450000.00);

-- Mouvement de paiement partiel (200,000 Ar)
INSERT INTO mouvement (id, id_type_mouvement, id_achat, date_mouvement) VALUES 
    (3, 1, 2, '2026-06-15');

INSERT INTO mouvement_detail (id_mouvement, id_compte_compta, debit, credit) VALUES
    (3, 2, 200000.00, 0.00),   -- Débit fournisseur (réduction dette)
    (3, 5, 0.00, 200000.00);    -- Crédit caisse


-- 3.3 ACHAT 3 : Dette de 600,000 Ar (payée intégralement)
INSERT INTO achat (id, id_fournisseur, date_achat, description) VALUES 
    (3, 3, '2026-06-20', 'Achat de bovins - Dette 600,000 Ar - Payé totalement');
    
INSERT INTO bovin (id_race, id_lot, poids_initial, poids_actuel, date_naissance, date_arrivee) VALUES
    (1, 3, 160.00, 190.00, '2025-10-01', '2026-06-20');

INSERT INTO achat_bovin_detail (id_achat, id_bovin, prix_achat) VALUES
    (3, (SELECT MAX(id) FROM bovin), 600000.00);

-- Mouvement d'achat
INSERT INTO mouvement (id, id_type_mouvement, id_achat, date_mouvement) VALUES 
    (4, 1, 3, '2026-06-20');

INSERT INTO mouvement_detail (id_mouvement, id_compte_compta, debit, credit) VALUES
    (4, 1, 600000.00, 0.00),
    (4, 2, 0.00, 600000.00);

-- Paiement total
INSERT INTO mouvement (id, id_type_mouvement, id_achat, date_mouvement) VALUES 
    (5, 1, 3, '2026-06-25');

INSERT INTO mouvement_detail (id_mouvement, id_compte_compta, debit, credit) VALUES
    (5, 2, 600000.00, 0.00),
    (5, 6, 0.00, 600000.00);


-- 3.4 ACHAT 4 : Dette de 1,200,000 Ar (non payée)
INSERT INTO achat (id, id_fournisseur, date_achat, description) VALUES 
    (4, 1, '2026-06-26', 'Achat de 2 bovins - Grosse dette 1,200,000 Ar');
    
INSERT INTO bovin (id_race, id_lot, poids_initial, poids_actuel, date_naissance, date_arrivee) VALUES
    (1, 3, 180.00, 210.00, '2025-11-15', '2026-06-26'),
    (1, 3, 190.00, 220.00, '2025-11-20', '2026-06-26');

INSERT INTO achat_bovin_detail (id_achat, id_bovin, prix_achat) VALUES
    (4, (SELECT MAX(id)-1 FROM bovin), 600000.00),
    (4, (SELECT MAX(id) FROM bovin), 600000.00);

-- Mouvement d'achat
INSERT INTO mouvement (id, id_type_mouvement, id_achat, date_mouvement) VALUES 
    (6, 1, 4, '2026-06-26');

INSERT INTO mouvement_detail (id_mouvement, id_compte_compta, debit, credit) VALUES
    (6, 1, 1200000.00, 0.00),
    (6, 2, 0.00, 1200000.00);


-- 4. CRÉER LA VUE DES DETTES (si elle n'existe pas)
-- =============================================

DROP VIEW IF EXISTS v_dette_fournisseur;

CREATE OR REPLACE VIEW v_dette_fournisseur AS
SELECT 
    mvt.id_achat,
    SUM(COALESCE(md.credit, 0)) AS total_credit_fournisseur,
    SUM(COALESCE(md.debit, 0)) AS total_debit_fournisseur,
    SUM(COALESCE(md.credit, 0)) - SUM(COALESCE(md.debit, 0)) AS reste_a_payer
FROM mouvement mvt
JOIN mouvement_detail md ON mvt.id = md.id_mouvement
WHERE md.id_compte_compta = 2  -- Compte fournisseur
AND mvt.id_achat IS NOT NULL
GROUP BY mvt.id_achat
HAVING SUM(COALESCE(md.credit, 0)) > SUM(COALESCE(md.debit, 0));


-- 5. VÉRIFIER LES RÉSULTATS
-- =============================================

-- Voir toutes les dettes
SELECT * FROM v_dette_fournisseur ORDER BY id_achat;

-- Voir les détails avec les noms des fournisseurs
SELECT 
    v.id_achat,
    f.nom AS fournisseur,
    a.description,
    a.date_achat,
    v.total_credit_fournisseur AS montant_total,
    v.total_debit_fournisseur AS montant_paye,
    v.reste_a_payer AS reste_a_payer
FROM v_dette_fournisseur v
JOIN achat a ON v.id_achat = a.id
JOIN fournisseur f ON a.id_fournisseur = f.id
ORDER BY v.id_achat;

-- Voir le résumé des dettes
SELECT 
    COUNT(*) AS nombre_dettes,
    SUM(reste_a_payer) AS total_dettes,
    AVG(reste_a_payer) AS moyenne_dette,
    MAX(reste_a_payer) AS max_dette,
    MIN(reste_a_payer) AS min_dette
FROM v_dette_fournisseur;


-- 6. RÉSULTATS ATTENDUS
-- =============================================
-- 
-- id_achat | total_credit_fournisseur | total_debit_fournisseur | reste_a_payer
-- ----------+--------------------------+-------------------------+---------------
--         1 |                250000.00 |                    0.00 |     250000.00
--         2 |                450000.00 |               200000.00 |     250000.00
--         4 |               1200000.00 |                    0.00 |    1200000.00
--
-- Explications:
-- - Achat 1: Dette totale de 250,000 Ar (non payée)
-- - Achat 2: Dette de 450,000 Ar, payée 200,000 Ar, reste 250,000 Ar
-- - Achat 3: Payée intégralement, donc n'apparaît PAS dans la vue
-- - Achat 4: Dette de 1,200,000 Ar (non payée)


-- 7. NETTOYAGE (optionnel)
-- =============================================
-- Pour supprimer les données de test :
/*
DELETE FROM mouvement_detail WHERE id_mouvement IN (SELECT id FROM mouvement WHERE id_achat IN (1, 2, 3, 4));
DELETE FROM mouvement WHERE id_achat IN (1, 2, 3, 4);
DELETE FROM achat_bovin_detail WHERE id_achat IN (1, 2, 3, 4);
DELETE FROM achat WHERE id IN (1, 2, 3, 4);
*/