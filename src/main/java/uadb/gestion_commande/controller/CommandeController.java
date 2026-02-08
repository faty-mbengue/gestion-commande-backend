package uadb.gestion_commande.controller;

import uadb.gestion_commande.entity.Commande;
import uadb.gestion_commande.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "http://localhost:3000")
public class CommandeController {

    @Autowired
    private CommandeRepository commandeRepository;

    @GetMapping
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Commande getCommandeById(@PathVariable Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Commande createCommande(@RequestBody Commande commande) {
        return commandeRepository.save(commande);
    }

    @PutMapping("/{id}")
    public Commande updateCommande(@PathVariable Long id, @RequestBody Commande commande) {
        commande.setNumCommande(id);
        return commandeRepository.save(commande);
    }

    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id) {
        commandeRepository.deleteById(id);
    }
}