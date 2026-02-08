package uadb.gestion_commande.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;  // ← AJOUTEZ CET IMPORT
import java.math.BigDecimal;

@Entity
@Table(name = "ligne_commande")
@Data
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    @JsonIgnoreProperties({"ligneCommandes"})  // ← AJOUTEZ CETTE LIGNE
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    @JsonIgnoreProperties({"ligneCommandes"})  // ← AJOUTEZ CETTE LIGNE
    private Produit produit;

    @Column(nullable = false)
    private Integer quantite;

    @Column(name = "prix_unitaire", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixUnitaire;

    // Constructeur avec paramètres
    public LigneCommande() {}

    public LigneCommande(Commande commande, Produit produit, Integer quantite, BigDecimal prixUnitaire) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    // Calcul du prix total TTC pour cette ligne
    @Transient
    public Double getPrixTotalTTC() {
        if (prixUnitaire == null || quantite == null) {
            return 0.0;
        }
        return prixUnitaire.doubleValue() * quantite;
    }
}