# Activité Pratique N°2 : ORM, JPA, Hibernate et Spring Data

Compte rendu de l'activité pratique portant sur la mise en œuvre de la couche de persistance d'une application Java EE avec **Spring Boot**, **Spring Data JPA**, **Hibernate** et une base de données relationnelle en mémoire **H2**.

**Étudiant :** Oussama Boustane  
**Établissement :** Université Mundiapolis — Cycle Ingénieur Informatique  
**Module :** Architecture JEE & Systèmes Distribués  
**Dépôt GitHub :** oboussamaboustane-eng/productapp

---

# Table des matières

1. Objectifs de l'activité  
2. Architecture et concepts clés  
3. Stack technique  
4. Structure du projet  
5. Étapes d'implémentation  
6. Résultats d'exécution  
7. Installation et exécution

---

# Objectifs de l'activité

- Initialiser un projet Spring Boot via **Spring Initializr** sous IntelliJ IDEA.
- Comprendre le mapping Objet-Relationnel (**ORM**) à travers la spécification **JPA** et son implémentation **Hibernate**.
- Configurer une base de données **H2** et activer sa console d'administration web.
- Exploiter **Spring Data JPA** (`JpaRepository`) pour automatiser les opérations CRUD et créer des méthodes de recherche personnalisées.
- Valider l'ensemble des traitements (ajout, consultation, recherche, mise à jour et suppression) au démarrage de l'application.

---

# Architecture et concepts clés

L'application repose sur une architecture en couches assurant un **couplage faible** :

- **JPA (Java Persistence API)** : spécification standard Java définissant les annotations de mapping (`@Entity`, `@Id`, `@GeneratedValue`).
- **Hibernate** : implémentation concrète (moteur ORM) de JPA, chargée de générer dynamiquement les requêtes SQL et de synchroniser les objets Java avec les tables relationnelles.
- **Spring Data JPA** : couche d'abstraction au-dessus de JPA/Hibernate qui élimine le code répétitif de la couche DAO grâce à l'interface générique `JpaRepository`.

---

# Stack technique

| Technologie | Version / Détail | Rôle |
|------------|------------------|------|
| Java (JDK) | Java 21 | Langage de programmation |
| Spring Boot | 3.x | Framework d'application et conteneur IoC |
| Spring Data JPA | Hibernate ORM | Persistance des données et mapping SQL |
| H2 Database | En mémoire (mem) | SGBD relationnel léger pour les tests |
| Lombok | Annotations | Génération automatique du code |
| Maven | Gestionnaire de build | Gestion des dépendances et cycle de vie |

---

# Structure du projet

```text {preview="false"}
productapp
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java/net/oussama/productapp
    │   │   ├── ProductappApplication.java
    │   │   ├── entities
    │   │   │   └── Product.java
    │   │   └── repository
    │   │       └── ProductRepository.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java/net/oussama/productapp
            └── ProductappApplicationTests.java
```

---

# Étapes d'implémentation

## 5.1 Création de l'entité JPA `Product`

```java {preview="false"}
package net.oussama.productapp.entities;

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
```

## 5.2 Configuration de la persistance (`application.properties`)

```properties {preview="false"}
spring.application.name=productapp
server.port=8080

spring.datasource.url=jdbc:h2:mem:products-db
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.h2.console.enabled=true

spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
```

## 5.3 Interface Spring Data `ProductRepository`

```java {preview="false"}
package net.oussama.productapp.repository;

import net.oussama.productapp.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainsIgnoreCase(String keyword);

    List<Product> findByPriceGreaterThan(double price);
}
```

## 5.4 Tests des opérations CRUD (`CommandLineRunner`)

Un bean `CommandLineRunner` exécute séquentiellement les opérations suivantes au démarrage de l'application :

- Ajout de trois produits (Ordinateur, Imprimante, Souris).
- Consultation de tous les produits via `findAll()`.
- Consultation d'un produit par identifiant via `findById()`.
- Recherche multicritère via des méthodes personnalisées.
- Mise à jour du prix et de la quantité d'un produit.
- Suppression d'un produit via `deleteById()`.

---

# Résultats d'exécution

Les tests réalisés au démarrage de l'application démontrent le bon fonctionnement des opérations CRUD.

- Les produits sont correctement enregistrés dans la base H2.
- Les opérations de lecture retournent les résultats attendus.
- Les méthodes de recherche personnalisées fonctionnent correctement.
- Les modifications apportées aux produits sont bien persistées.
- La suppression d'un produit est correctement prise en compte.

Hibernate génère automatiquement les requêtes SQL nécessaires et les affiche dans la console grâce à l'option :

```properties {preview="false"}
spring.jpa.show-sql=true
```

La base de données peut être consultée via la console H2 :

```text {preview="false"}
http://localhost:8080/h2-console
```

---

# Installation et exécution

## Cloner le dépôt

```bash {preview="false"}
git clone https://github.com/oboussamaboustane-eng/productapp.git
cd productapp
```

## Ouvrir le projet

Ouvrir le projet dans **IntelliJ IDEA** et laisser Maven télécharger les dépendances nécessaires.

## Lancer l'application

Exécuter la classe :

```text {preview="false"}
ProductappApplication
```

ou les tests :

```text {preview="false"}
ProductappApplicationTests
```

## Accéder à la console H2

```text {preview="false"}
http://localhost:8080/h2-console
```

Paramètres de connexion :

```text {preview="false"}
JDBC URL : jdbc:h2:mem:products-db
User      : sa
Password  : (vide)
```
