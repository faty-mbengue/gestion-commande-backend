package uadb.gestion_commande.entity;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;  // ← AJOUTEZ CET IMPORT
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_client")
    private Long numClient;

    private String civilite; // "M.", "Mme", "Mlle"

    @Column(nullable = false)
    private String nom;

    private String prenom;

    private String adresse;

    @Column(name = "code_postal")
    private String codePostal;

    private String ville;

    private String pays;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore  // ← C'EST LA CLÉ ! Évite la référence circulaire
    private List<Commande> commandes = new ArrayList<>();
}