package uadb.gestion_commande.repository;

import uadb.gestion_commande.entity.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {
}