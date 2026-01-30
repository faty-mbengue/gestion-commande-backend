package uadb.gestion_commande.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return """
               🚀 Gestion Commande API
               ========================
               Endpoints disponibles:
               - /clients           : Gestion des clients
               - /produits          : Gestion des produits
               - /commandes         : Gestion des commandes
               - /h2-console       : Console H2 Database
               - /swagger-ui.html  : Documentation API
               """;
    }
}