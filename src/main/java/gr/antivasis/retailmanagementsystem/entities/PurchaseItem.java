package gr.antivasis.retailmanagementsystem.entities;

import gr.antivasis.retailmanagementsystem.dtos.products.CreateUpdateProductDTO;
import gr.antivasis.retailmanagementsystem.dtos.purchases.CreatePurchaseItemDTO;
import gr.antivasis.retailmanagementsystem.repositories.ProductRepository;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "purchase_item")
public class PurchaseItem {
    @EmbeddedId
    private PurchaseItemId id;

    @MapsId("productId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @MapsId("purchaseId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    public PurchaseItem(CreatePurchaseItemDTO item, ProductRepository productRepository) {
        this.fromDTO(item, productRepository);
    }

    public PurchaseItem fromDTO(CreatePurchaseItemDTO item, ProductRepository productRepository){
        this.quantity = item.quantity();
        this.product = productRepository.findById(item.productId()).orElseThrow();
        return this;
    }

}