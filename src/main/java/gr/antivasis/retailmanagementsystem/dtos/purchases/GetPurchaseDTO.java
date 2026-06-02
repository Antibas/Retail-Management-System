package gr.antivasis.retailmanagementsystem.dtos.purchases;

import gr.antivasis.retailmanagementsystem.entities.Purchase;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link gr.antivasis.retailmanagementsystem.entities.Purchase}
 */
public record GetPurchaseDTO(UUID id, UUID customerId, Integer totalAmount, LocalDateTime createdAt,
                             LocalDateTime purchasedAt, List<GetPurchaseItemDTO> items) implements Serializable {

  public GetPurchaseDTO(Purchase purchase){
    this(
            purchase.getId(),
            purchase.getCustomer().getId(),
            purchase.getPurchaseItems().stream().mapToInt(item -> item.getQuantity() * item.getUnitPrice().intValue()).sum(),
            purchase.getCreatedAt(),
            purchase.getPurchasedAt(),
            purchase.getPurchaseItems().stream().map(GetPurchaseItemDTO::new).toList()
    );
  }
}