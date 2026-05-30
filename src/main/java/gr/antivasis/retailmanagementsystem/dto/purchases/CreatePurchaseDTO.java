package gr.antivasis.retailmanagementsystem.dto.purchases;

import java.util.List;
import java.util.UUID;

public record CreatePurchaseDTO(UUID customerId, List<CreatePurchaseItemDTO> items) {
}
