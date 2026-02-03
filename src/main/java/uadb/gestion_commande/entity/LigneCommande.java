package uadb.gestion_commande.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ligne_commandes")
@Data
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column(nullable = false)
    private Integer quantite;

    @Column(name = "prix_ttc", nullable = false)
    private Double prixTTC;

    // Calcul du prix total pour cette ligne
    @Transient
    public Double getPrixTotalTTC() {
        return prixTTC * quantite;
    }
}
