-- 1. Vue pour obtenir les achats avec leur total
CREATE OR REPLACE VIEW v_achat_total AS
SELECT 
    a.id,
    a.id_fournisseur,
    a.date_achat,
    a.description,

    COALESCE(b.total_prix_bovin, 0) AS total_prix_bovin,
    COALESCE(al.total_prix_aliment, 0) AS total_prix_aliment,

    COALESCE(b.total_prix_bovin, 0) + COALESCE(al.total_prix_aliment, 0) AS total_achat

FROM achat a

LEFT JOIN (
    SELECT 
        id_achat,
        SUM(prix_achat) AS total_prix_bovin
    FROM achat_bovin_detail
    GROUP BY id_achat
) b ON a.id = b.id_achat

LEFT JOIN (
    SELECT 
        id_achat,
        SUM(prix_unitaire * quantite) AS total_prix_aliment
    FROM achat_aliment_detail
    GROUP BY id_achat
) al ON a.id = al.id_achat;


-- 2. Vue pour obtenir les dettes restantes par achat envers les fournisseurs
CREATE OR REPLACE VIEW v_dette_fournisseur AS
SELECT 
    mvt.id_achat,
    SUM(COALESCE(md.credit, 0)) AS total_credit_fournisseur,
    SUM(COALESCE(md.debit, 0)) AS total_debit_fournisseur,
    SUM(COALESCE(md.credit, 0)) - SUM(COALESCE(md.debit, 0)) AS reste_a_payer
FROM mouvement mvt
JOIN mouvement_compta md 
    ON mvt.id = md.id_mouvement
WHERE md.id_compte_compta = 2
AND mvt.id_achat IS NOT NULL
GROUP BY mvt.id_achat
HAVING SUM(COALESCE(md.credit, 0)) > SUM(COALESCE(md.debit, 0));