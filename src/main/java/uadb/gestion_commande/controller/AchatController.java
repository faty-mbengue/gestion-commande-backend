package uadb.gestion_commande.controller;

import uadb.gestion_commande.entity.*;
import uadb.gestion_commande.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/achat")
@CrossOrigin(origins = "http://localhost:3000")
public class AchatController {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private LignePanierRepository lignePanierRepository;

    @Autowired
    private LigneCommandeRepository ligneCommandeRepository;

    // Étape 1: Voir les produits
    @GetMapping("/produits")
    public List<Produit> voirProduits() {
        return produitRepository.findAll();
    }

    // Étape 2: Ajouter au panier
    @PostMapping("/panier/ajouter")
    public Panier ajouterAuPanier(@RequestParam Long clientId,
                                  @RequestParam Long produitId,
                                  @RequestParam Integer quantite) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        // CORRECTION : Utilisez findByClient_NumClient
        Panier panier = panierRepository.findByClient_NumClient(clientId)
                .orElseGet(() -> {
                    Panier newPanier = new Panier();
                    newPanier.setClient(client);
                    return panierRepository.save(newPanier);
                });

        // Vérifier si le produit est déjà dans le panier
        Optional<LignePanier> existingLine = panier.getLignesPanier().stream()
                .filter(lp -> lp.getProduit().getNumProduit().equals(produitId))
                .findFirst();

        if (existingLine.isPresent()) {
            // Mettre à jour la quantité
            LignePanier ligne = existingLine.get();
            ligne.setQuantite(ligne.getQuantite() + quantite);
        } else {
            // Créer une nouvelle ligne
            LignePanier nouvelleLigne = new LignePanier();
            nouvelleLigne.setPanier(panier);
            nouvelleLigne.setProduit(produit);
            nouvelleLigne.setQuantite(quantite);
            nouvelleLigne.setPrixUnitaire(produit.getPrixTTC());
            panier.getLignesPanier().add(nouvelleLigne);
        }

        // Recalculer le total
        recalculerTotal(panier);

        return panierRepository.save(panier);
    }

    // Étape 3: Voir le panier
    @GetMapping("/panier/{clientId}")
    public Panier voirPanier(@PathVariable Long clientId) {
        // CORRECTION : Utilisez findByClient_NumClient
        return panierRepository.findByClient_NumClient(clientId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));
    }

    // Étape 4: Modifier le panier
    @PutMapping("/panier/modifier")
    public Panier modifierPanier(@RequestParam Long ligneId,
                                 @RequestParam Integer nouvelleQuantite) {
        LignePanier ligne = lignePanierRepository.findById(ligneId)
                .orElseThrow(() -> new RuntimeException("Ligne non trouvée"));

        if (nouvelleQuantite <= 0) {
            lignePanierRepository.delete(ligne);
        } else {
            ligne.setQuantite(nouvelleQuantite);
            lignePanierRepository.save(ligne);
        }

        Panier panier = ligne.getPanier();
        recalculerTotal(panier);

        return panierRepository.save(panier);
    }

    // Étape 5: Créer la commande à partir du panier
    @PostMapping("/commande/creer/{clientId}")
    public Commande creerCommande(@PathVariable Long clientId) {
        // CORRECTION : Utilisez findByClient_NumClient
        Panier panier = panierRepository.findByClient_NumClient(clientId)
                .orElseThrow(() -> new RuntimeException("Panier non trouvé"));

        if (panier.getLignesPanier().isEmpty()) {
            throw new RuntimeException("Le panier est vide");
        }

        // Créer la commande
        Commande commande = new Commande();
        commande.setClient(panier.getClient());

        // Sauvegarder la commande d'abord
        Commande savedCommande = commandeRepository.save(commande);

        // Convertir les lignes du panier en lignes de commande
        for (LignePanier lignePanier : panier.getLignesPanier()) {
            LigneCommande ligneCommande = new LigneCommande();
            ligneCommande.setCommande(savedCommande);
            ligneCommande.setProduit(lignePanier.getProduit());
            ligneCommande.setQuantite(lignePanier.getQuantite());
            ligneCommande.setPrixUnitaire(lignePanier.getPrixUnitaire());
            ligneCommandeRepository.save(ligneCommande);
        }

        // Vider le panier après création de la commande
        panier.getLignesPanier().clear();
        panier.setTotal(BigDecimal.ZERO);
        panierRepository.save(panier);

        return savedCommande;
    }

    // Étape 6: Finaliser la commande (paiement)
    @PostMapping("/commande/finaliser/{commandeId}")
    public String finaliserCommande(@PathVariable Long commandeId) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        return "Commande " + commandeId + " finalisée avec succès. Total: " + commande.getTotalTTC();
    }

    private void recalculerTotal(Panier panier) {
        BigDecimal total = panier.getLignesPanier().stream()
                .map(l -> l.getPrixUnitaire().multiply(BigDecimal.valueOf(l.getQuantite())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        panier.setTotal(total);
    }
}