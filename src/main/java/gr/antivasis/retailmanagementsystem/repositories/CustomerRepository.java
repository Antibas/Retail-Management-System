package gr.antivasis.retailmanagementsystem.repositories;

import gr.antivasis.retailmanagementsystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("SELECT c FROM Customer c WHERE c.isActive = TRUE AND (c.email LIKE %:query% OR c.phone LIKE %:query% OR c.firstName LIKE %:query% OR c.lastName LIKE %:query%)")
    List<Customer> findByQuery(String query);
    Optional<Customer> findByIdAndIsActiveTrue(UUID id);
//    Optional<Customer> findByEmailAndIsActiveTrue(String email);
    List<Customer> findAllByIsActiveTrue();
}