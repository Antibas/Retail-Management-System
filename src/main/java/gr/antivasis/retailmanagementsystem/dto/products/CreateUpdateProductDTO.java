package gr.antivasis.retailmanagementsystem.dto.products;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link gr.antivasis.retailmanagementsystem.entities.Product}
 */
public record CreateUpdateProductDTO(String name, String description, BigDecimal price,
                                     String sku) implements Serializable {
}