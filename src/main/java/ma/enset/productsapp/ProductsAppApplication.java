package ma.enset.productsapp;

import ma.enset.productsapp.entities.Product;
import ma.enset.productsapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProductsAppApplication implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProductsAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Ajouter des produits
        productRepository.save(
                new Product(null, "PC", 12000, 10)
        );

        productRepository.save(
                new Product(null, "Imprimante", 3000, 5)
        );

        productRepository.save(
                new Product(null, "Clavier", 250, 20)
        );

        // Afficher tous les produits
        System.out.println("******** ALL PRODUCTS ********");

        productRepository.findAll().forEach(System.out::println);

        // Afficher un produit
        System.out.println("******** ONE PRODUCT ********");

        Product p = productRepository.findById(1L).orElse(null);

        System.out.println(p);

        // Rechercher des produits
        System.out.println("******** SEARCH PRODUCT ********");

        productRepository.findByNameContains("P")
                .forEach(System.out::println);

        // Modifier un produit
        System.out.println("******** UPDATE PRODUCT ********");

        Product p1 = productRepository.findById(1L).orElse(null);

        if (p1 != null) {
            p1.setPrice(15000);
            productRepository.save(p1);
        }

        System.out.println(productRepository.findById(1L).orElse(null));

        // Supprimer un produit
        System.out.println("******** DELETE PRODUCT ********");

        productRepository.deleteById(2L);

        productRepository.findAll().forEach(System.out::println);
    }
}