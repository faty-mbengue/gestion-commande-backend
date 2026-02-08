package uadb.gestion_commande.controller;

import uadb.gestion_commande.entity.Produit;
import uadb.gestion_commande.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "http://localhost:3000")
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    @GetMapping("/{id}")
    public Produit getProduitById(@PathVariable Long id) {
        return produitRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Produit createProduit(@RequestBody Produit produit) {
        return produitRepository.save(produit);
    }

    @PutMapping("/{id}")
    public Produit updateProduit(@PathVariable Long id, @RequestBody Produit produit) {
        produit.setNumProduit(id);
        return produitRepository.save(produit);
    }

    @DeleteMapping("/{id}")
    public void deleteProduit(@PathVariable Long id) {
        produitRepository.deleteById(id);
    }
}