package gr.antivasis.retailmanagementsystem.repositories;

import gr.antivasis.retailmanagementsystem.entities.PurchaseItem;
import gr.antivasis.retailmanagementsystem.entities.PurchaseItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, PurchaseItemId> {
    PurchaseItem findByPurchaseIdAndProductId(UUID purchaseId, UUID productId);
}