package gr.antivasis.retailmanagementsystem.repositories;

import gr.antivasis.retailmanagementsystem.entities.PurchaseItem;
import gr.antivasis.retailmanagementsystem.entities.PurchaseItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseItemRepository extends JpaRepository<PurchaseItem, PurchaseItemId> {
}