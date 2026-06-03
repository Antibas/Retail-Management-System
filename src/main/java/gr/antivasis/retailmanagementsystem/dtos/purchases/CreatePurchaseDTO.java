package gr.antivasis.retailmanagementsystem.dtos.purchases;

import java.util.List;

public record CreatePurchaseDTO(Integer redeemedPoints, List<CreatePurchaseItemDTO> items) {

    public CreatePurchaseDTO {
        if (redeemedPoints == null || redeemedPoints < 0) {
            redeemedPoints = 0;
        }
    }
}
