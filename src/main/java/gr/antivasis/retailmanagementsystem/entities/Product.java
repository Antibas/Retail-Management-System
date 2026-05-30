package gr.antivasis.retailmanagementsystem.entities;

import gr.antivasis.retailmanagementsystem.dtos.products.CreateUpdateProductDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Product(CreateUpdateProductDTO product) {
        this.fromDTO(product);
    }

    public Product fromDTO(CreateUpdateProductDTO product){
        this.name = product.name();
        this.description = product.description();
        this.price = product.price();
        this.sku = product.sku();
        return this;
    }
}