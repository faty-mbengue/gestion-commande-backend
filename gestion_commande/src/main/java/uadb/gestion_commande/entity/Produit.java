package uadb.gestion_commande.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produits")
@Data
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_produit")
    private Long numProduit;

    private String famille; // "Electronique", "Alimentaire", etc.

    @Column(nullable = false)
    private String nom;

    @Column(name = "prix_ht", nullable = false, precision = 10, scale = 2)
    private BigDecimal prixHT;

    @Column(name = "prix_promo_ht", precision = 10, scale = 2)
    private BigDecimal prixPromoHT;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal tva = BigDecimal.valueOf(20.00); // 20% par défaut

    private String designation;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommande> ligneCommandes = new ArrayList<>();

    // Calcul du prix TTC
    @Transient
    public BigDecimal getPrixTTC() {
        BigDecimal tauxTVA = BigDecimal.ONE.add(tva.divide(BigDecimal.valueOf(100)));
        return prixHT.multiply(tauxTVA);
    }
}