package gr.antivasis.retailmanagementsystem.repositories;

import gr.antivasis.retailmanagementsystem.entities.Customer;
import gr.antivasis.retailmanagementsystem.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE :query IN p.name OR :query IN p.description OR :query IN p.sku")
    List<Product> findByQuery(String query);
}