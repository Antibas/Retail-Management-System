package gr.antivasis.retailmanagementsystem.dto.products;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link gr.antivasis.retailmanagementsystem.entities.Product}
 */
public record GetProductDTO(UUID id, String name, String description, BigDecimal price, String sku,
                            LocalDateTime createdAt, LocalDateTime updatedAt) implements Serializable {

    public GetProductDTO(gr.antivasis.retailmanagementsystem.entities.Product product){
        this(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getSku(), product.getCreatedAt(), product.getUpdatedAt());
    }
}