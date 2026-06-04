package gr.antivasis.retailmanagementsystem.dtos.purchases;

import gr.antivasis.retailmanagementsystem.entities.Purchase;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link gr.antivasis.retailmanagementsystem.entities.Purchase}
 */
public record GetPurchaseDTO(UUID id, UUID customerId, Double totalAmount, LocalDateTime createdAt,
                             LocalDateTime purchasedAt, List<GetPurchaseItemDTO> items) implements Serializable {

    public GetPurchaseDTO(Purchase purchase){
        this(purchase, 0);
    }

  public GetPurchaseDTO(Purchase purchase, double totalAmount){
    this(
            purchase.getId(),
            purchase.getCustomer().getId(),
            totalAmount == 0? purchase.getTotalAmount(): totalAmount,
            purchase.getCreatedAt(),
            purchase.getPurchasedAt(),
            purchase.getPurchaseItems().stream().map(GetPurchaseItemDTO::new).toList()
    );
  }
}