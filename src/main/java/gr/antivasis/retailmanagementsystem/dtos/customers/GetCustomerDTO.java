package gr.antivasis.retailmanagementsystem.dtos.customers;

import gr.antivasis.retailmanagementsystem.entities.Customer;
import gr.antivasis.retailmanagementsystem.enums.CustomerTier;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link Customer}
 */
public record GetCustomerDTO(UUID id, String firstName, String lastName, String email, String phone, Boolean isActive,
                             Double points, CustomerTier tier,
                             LocalDateTime createdAt, LocalDateTime updatedAt) implements Serializable {
    public GetCustomerDTO(Customer customer){
        this(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getIsActive(),
                customer.getPoints(),
                CustomerTier.fromPoints(customer.getLifetimePoints()),
                customer.getCreatedAt(),
                customer.getUpdatedAt());
    }
}