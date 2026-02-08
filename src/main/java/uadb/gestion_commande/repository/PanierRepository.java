package uadb.gestion_commande.repository;

import uadb.gestion_commande.entity.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PanierRepository extends JpaRepository<Panier, Long> {
    // Utilisez "numClient" (avec n minuscule) car c'est le nom de l'attribut
    Optional<Panier> findByClient_NumClient(Long numClient);
}