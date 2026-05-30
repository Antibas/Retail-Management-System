package gr.antivasis.retailmanagementsystem.dto.purchases;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link gr.antivasis.retailmanagementsystem.entities.PurchaseItem}
 */
public record CreatePurchaseItemDTO(UUID productId, Integer quantity) implements Serializable {
}