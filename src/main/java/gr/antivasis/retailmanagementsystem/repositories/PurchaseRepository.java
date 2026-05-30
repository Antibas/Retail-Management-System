package gr.antivasis.retailmanagementsystem.repositories;

import gr.antivasis.retailmanagementsystem.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
    List<Purchase> findByCustomerId(UUID customerId);
}