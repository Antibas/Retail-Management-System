package gr.antivasis.retailmanagementsystem.dto.purchases;

import gr.antivasis.retailmanagementsystem.entities.Purchase;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO for {@link gr.antivasis.retailmanagementsystem.entities.Purchase}
 */
public record GetPurchaseDTO(UUID id, UUID customerId, Integer totalAmount, LocalDateTime createdAt,
                             LocalDateTime purchasedAt) implements Serializable {

  public GetPurchaseDTO(Purchase purchase){
    this(purchase.getId(), purchase.getCustomer().getId(), purchase.getTotalAmount(), purchase.getCreatedAt(), purchase.getPurchasedAt());
  }
}