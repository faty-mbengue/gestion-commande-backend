package uadb.gestion_commande.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "panier")
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LignePanier> lignesPanier = new ArrayList<>();

    private BigDecimal total = BigDecimal.ZERO;

    // Constructeurs
    public Panier() {}

    public Panier(Client client) {
        this.client = client;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<LignePanier> getLignesPanier() {
        return lignesPanier;
    }

    public void setLignesPanier(List<LignePanier> lignesPanier) {
        this.lignesPanier = lignesPanier;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}