package uadb.gestion_commande.repository;

import uadb.gestion_commande.entity.LignePanier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LignePanierRepository extends JpaRepository<LignePanier, Long> {
}