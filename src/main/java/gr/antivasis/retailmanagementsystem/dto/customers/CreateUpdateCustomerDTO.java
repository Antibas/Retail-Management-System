package gr.antivasis.retailmanagementsystem.dto.customers;

import gr.antivasis.retailmanagementsystem.entities.Customer;

import java.io.Serializable;

/**
 * DTO for {@link Customer}
 */
public record CreateUpdateCustomerDTO(String firstName, String lastName, String email, String phone) implements Serializable {
}