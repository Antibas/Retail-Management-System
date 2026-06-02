package gr.antivasis.retailmanagementsystem.entities;

import gr.antivasis.retailmanagementsystem.dtos.customers.CreateUpdateCustomerDTO;
import gr.antivasis.retailmanagementsystem.enums.PointsBatchStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

import java.time.LocalDateTime;
import java.util.*;

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

    @NonNull
    @OneToMany(mappedBy = "customer")
    private List<PointsBatch> pointsBatches = new ArrayList<>();

    @Column(name = "lifetime_points")
    private Integer lifetimePoints;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public double getPoints() {
        return this.getPointsBatches()
                .stream()
                .filter(p -> p.getStatus() == PointsBatchStatus.REDEEMABLE)
                .mapToDouble(PointsBatch::getPoints).sum();
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