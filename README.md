Activité Pratique N°2 : ORM, JPA, Hibernate et Spring Data
Compte rendu de l'activité pratique portant sur la mise en œuvre de la couche de persistance d'une application Java EE avec Spring Boot, Spring Data JPA, Hibernate et une base de données relationnelle en mémoire H2.

Étudiant : EL AZZOUZI ABDELMOGHIT
Établissement : Université Mundiapolis — Cycle Ingénieur Informatique
Module : Architecture JEE & Systèmes Distribués

Table des matières
Objectifs de l'activité
Architecture et concepts clés
Stack technique
Structure du projet
Étapes d'implémentation
Résultats d'exécution
Installation et exécution
Objectifs de l'activité
Initialiser un projet Spring Boot via Spring Initializr sous IntelliJ IDEA.
Comprendre le mapping Objet-Relationnel (ORM) à travers la spécification JPA et son implémentation Hibernate.
Configurer une base de données H2 et activer sa console d'administration web.
Exploiter Spring Data JPA (JpaRepository) pour automatiser les opérations CRUD et créer des méthodes de recherche personnalisées.
Valider l'ensemble des traitements (ajout, consultation, recherche, mise à jour et suppression) au démarrage de l'application.
Architecture et concepts clés
L'application repose sur une architecture en couches assurant un couplage faible :

JPA (Java Persistence API) : spécification standard Java définissant les annotations de mapping (@Entity, @Id, @GeneratedValue).
Hibernate : implémentation concrète de JPA chargée de générer les requêtes SQL et d'assurer la synchronisation entre les objets Java et les tables relationnelles.
Spring Data JPA : couche d'abstraction qui simplifie l'accès aux données grâce à l'interface générique JpaRepository.
Stack technique
Technologie	Version / Détail	Rôle
Java (JDK)	Java 21	Langage de programmation
Spring Boot	3.x	Framework d'application
Spring Data JPA	Hibernate ORM	Persistance des données
H2 Database	En mémoire	Base de données relationnelle
Lombok	Annotations	Génération automatique du code
Maven	Build Tool	Gestion des dépendances
Structure du projet
productapp
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── net
    │   │       └── ayman
    │   │           ├── ProductappApplication.java
    │   │           ├── entities
    │   │           │   └── Product.java
    │   │           └── repository
    │   │               └── ProductRepository.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java
Étapes d'implémentation
5.1 Création de l'entité JPA Product
package net.ayman.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;
    private int quantity;
}
5.2 Configuration de la persistance
Fichier application.properties :

spring.application.name=productapp
server.port=8080

spring.datasource.url=jdbc:h2:mem:products-db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
5.3 Création du Repository
package net.ayman.repository;

import net.ayman.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainsIgnoreCase(String keyword);

    List<Product> findByPriceGreaterThan(double price);
}
5.4 Validation des opérations CRUD
Au démarrage de l'application, un CommandLineRunner permet de réaliser automatiquement plusieurs opérations :

Ajout de nouveaux produits.
Affichage de tous les produits.
Recherche par identifiant.
Recherche personnalisée par nom.
Recherche des produits dont le prix dépasse un seuil donné.
Mise à jour d'un produit existant.
Suppression d'un produit.
Résultats d'exécution
Les différents tests exécutés au lancement de l'application confirment le bon fonctionnement de la couche de persistance.

Les opérations suivantes ont été validées avec succès :

Création de produits.
Consultation globale des données.
Consultation par identifiant.
Recherche via des méthodes personnalisées.
Modification des données.
Suppression d'enregistrements.
Hibernate génère automatiquement les requêtes SQL et les affiche dans la console grâce à la propriété :

spring.jpa.show-sql=true
L'accès à la base de données est également possible via la console H2 intégrée.

Installation et exécution
1. Cloner le dépôt
git clone <url-du-depot>
cd productapp
2. Ouvrir le projet
Ouvrir le projet dans IntelliJ IDEA et attendre le téléchargement automatique des dépendances Maven.

3. Exécuter l'application
Lancer la classe principale :

ProductappApplication
4. Accéder à la console H2
Ouvrir l'adresse suivante dans un navigateur :

http://localhost:8080/h2-console
Utiliser les paramètres suivants :

JDBC URL : jdbc:h2:mem:products-db
User      : sa
Password  :
Conclusion
Cette activité a permis de découvrir les mécanismes de persistance des données avec Spring Boot, JPA et Hibernate. L'utilisation de Spring Data JPA simplifie considérablement le développement de la couche d'accès aux données grâce aux repositories et aux méthodes de requête automatiques. Les tests réalisés ont confirmé le bon fonctionnement des opérations CRUD ainsi que l'intégration avec la base de données H2.
