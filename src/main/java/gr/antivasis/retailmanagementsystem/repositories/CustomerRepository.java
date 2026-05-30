package gr.antivasis.retailmanagementsystem.repositories;

import gr.antivasis.retailmanagementsystem.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    @Query("SELECT c FROM Customer c WHERE :query IN c.email OR :query IN c.phone OR :query IN c.firstName OR :query IN c.lastName")
    List<Customer> findByQuery(String query);
}