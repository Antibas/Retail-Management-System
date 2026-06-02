package gr.antivasis.retailmanagementsystem.repositories;

import gr.antivasis.retailmanagementsystem.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE p.isActive = TRUE AND (p.name LIKE %:query% OR p.description LIKE %:query% OR p.sku LIKE %:query%)")
    List<Product> findByQuery(String query);
    Optional<Product> findByIdAndIsActiveTrue(UUID id);
    List<Product> findAllByIsActiveTrue();
}