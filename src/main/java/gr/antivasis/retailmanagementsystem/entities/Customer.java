package gr.antivasis.retailmanagementsystem.entities;

import gr.antivasis.retailmanagementsystem.dtos.customers.CreateUpdateCustomerDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Customer(CreateUpdateCustomerDTO customer) {
        this.fromDTO(customer);
    }

    public Customer fromDTO(CreateUpdateCustomerDTO customer){
        this.firstName = customer.firstName();
        this.lastName = customer.lastName();
        this.email = customer.email();
        this.phone = customer.phone();
        return this;
    }
}