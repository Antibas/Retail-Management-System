package gr.antivasis.retailmanagementsystem.dtos.purchases;

import gr.antivasis.retailmanagementsystem.entities.PurchaseItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * DTO for {@link gr.antivasis.retailmanagementsystem.entities.PurchaseItem}
 */
public record GetPurchaseItemDTO(UUID productId, UUID purchaseId, Integer quantity,
                                 BigDecimal unitPrice) implements Serializable {

  public GetPurchaseItemDTO(PurchaseItem purchaseItem){
    this(purchaseItem.getProduct().getId(), purchaseItem.getPurchase().getId(), purchaseItem.getQuantity(), purchaseItem.getUnitPrice());
  }
}