├── src/main/java/uadb/gestion_commande/
│ ├── GestionCommandeApplication.java # Point d'entrée
│ ├── config/
│ │ └── CorsConfig.java # Configuration CORS
│ ├── controller/ # Contrôleurs REST
│ │ ├── ClientController.java
│ │ ├── ProduitController.java
│ │ ├── CommandeController.java
│ │ └── HomeController.java
│ ├── entity/ # Entités JPA
│ │ ├── Client.java
│ │ ├── Produit.java
│ │ ├── Commande.java
│ │ └── LigneCommande.java
│ ├── repository/ # Repositories Spring Data
│ │ ├── ClientRepository.java
│ │ ├── ProduitRepository.java
│ │ └── CommandeRepository.java
│ └── service/ # Logique métier
│ ├── ClientService.java
│ ├── ProduitService.java
│ └── CommandeService.java
├── src/main/resources/
│ ├── application.properties # Configuration
│ └── data.sql (optionnel) # Données initiales
└── pom.xml # Dépendances Maven

text

## 🚀 Démarrage Rapide

### Prérequis
- Java 17 ou supérieur
- Maven 3.6+
- Git

### Installation

```bash
# 1. Clonez le repository
git clone https://github.com/faty-mbengue/gestion-commande-backend.git
cd gestion-commande-backend

# 2. Compilez le projet
mvn clean compile

# 3. Lancez l'application
mvn spring-boot:run
