package uadb.gestion_commande.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commandes")
@Data
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_commande")
    private Long numCommande;

    @Column(name = "date_commande", nullable = false)
    private LocalDateTime dateCommande = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommande> ligneCommandes = new ArrayList<>();

    // Calcul du total de la commande
    @Transient
    public Double getTotalTTC() {
        return ligneCommandes.stream()
                .mapToDouble(LigneCommande::getPrixTotalTTC)
                .sum();
    }
}