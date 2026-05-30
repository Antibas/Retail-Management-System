package gr.antivasis.retailmanagementsystem.dtos.customers;

import gr.antivasis.retailmanagementsystem.entities.Customer;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * DTO for {@link Customer}
 */
public record GetCustomerDTO(UUID id, String firstName, String lastName, String email, String phone, Boolean isActive,
                             Instant createdAt, Instant updatedAt) implements Serializable {
    public GetCustomerDTO(Customer customer){
        this(customer.getId(), customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getIsActive(), customer.getCreatedAt(), customer.getUpdatedAt());
    }
}