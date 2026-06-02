package gr.antivasis.retailmanagementsystem.entities;

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
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "purchased_at")
    private LocalDateTime purchasedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @NonNull
    @OneToMany(mappedBy = "purchase")
    private List<PurchaseItem> purchaseItems = new ArrayList<>();


}