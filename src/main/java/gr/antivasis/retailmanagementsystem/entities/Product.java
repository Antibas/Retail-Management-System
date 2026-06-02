package gr.antivasis.retailmanagementsystem.entities;

import gr.antivasis.retailmanagementsystem.dtos.products.CreateUpdateProductDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "sku", nullable = false, length = 50)
    private String sku;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ColumnDefault("true")
    @Column(name = "is_active")
    private Boolean isActive;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Product(CreateUpdateProductDTO product) {
        this.fromDTO(product);
    }

    public Product fromDTO(CreateUpdateProductDTO product){
        this.name = product.name();
        this.description = product.description();
        this.price = product.price();
        this.sku = product.sku();
        this.stockQuantity = product.stockQuantity();
        return this;
    }
}