package uadb.gestion_commande.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore; // Import ajouté
import java.math.BigDecimal;

@Entity
@Table(name = "ligne_panier")
public class LignePanier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "panier_id")
    @JsonIgnore // Ajouter cette annotation
    private Panier panier;

    @ManyToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;

    private Integer quantite;

    @Column(precision = 10, scale = 2)
    private BigDecimal prixUnitaire;

    // ... reste du code inchangé


    // Constructeurs
    public LignePanier() {}

    public LignePanier(Panier panier, Produit produit, Integer quantite) {
        this.panier = panier;
        this.produit = produit;
        this.quantite = quantite;
        if (produit != null) {
            this.prixUnitaire = produit.getPrixTTC();
        }
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Panier getPanier() {
        return panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
        if (produit != null && this.prixUnitaire == null) {
            this.prixUnitaire = produit.getPrixTTC();
        }
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    @Transient
    public BigDecimal getSousTotal() {
        if (prixUnitaire == null || quantite == null) {
            return BigDecimal.ZERO;
        }
        return prixUnitaire.multiply(BigDecimal.valueOf(quantite));
    }
}