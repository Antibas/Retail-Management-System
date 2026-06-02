package gr.antivasis.retailmanagementsystem.services;

import gr.antivasis.retailmanagementsystem.dtos.purchases.CreatePurchaseItemDTO;
import gr.antivasis.retailmanagementsystem.dtos.purchases.GetPurchaseDTO;
import gr.antivasis.retailmanagementsystem.entities.Customer;
import gr.antivasis.retailmanagementsystem.entities.Purchase;
import gr.antivasis.retailmanagementsystem.entities.PurchaseItem;
import gr.antivasis.retailmanagementsystem.exceptions.ActionNotAllowedException;
import gr.antivasis.retailmanagementsystem.exceptions.ResourceNotFoundException;
import gr.antivasis.retailmanagementsystem.repositories.CustomerRepository;
import gr.antivasis.retailmanagementsystem.repositories.ProductRepository;
import gr.antivasis.retailmanagementsystem.repositories.PurchaseItemRepository;
import gr.antivasis.retailmanagementsystem.repositories.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public GetPurchaseDTO getById(UUID id) {
        return new GetPurchaseDTO(purchaseRepository.findById(id).orElseThrow());
    }

    @Transactional
    public GetPurchaseDTO create(UUID customerId, List<CreatePurchaseItemDTO> items) {
        Customer customer = customerRepository
                .findByIdAndIsActiveTrue(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", customerId)
                );
        if(!customer.getIsActive()) {
            throw new ActionNotAllowedException("Customer " + customerId + " is not active");
        }
        Purchase purchase = new Purchase();
        purchase.setCustomer(customer);
        purchase.setPurchasedAt(LocalDateTime.now());
        purchase = purchaseRepository.save(purchase);

        PurchaseItem item;
        List<PurchaseItem> purchaseItems = new ArrayList<>();
        for(CreatePurchaseItemDTO itemDTO : items) {
            item = new PurchaseItem(itemDTO, productRepository);
            item.setPurchase(purchase);

            purchaseItems.add(purchaseItemRepository.save(item));
        }
        purchase.setPurchaseItems(purchaseItems);
        return new GetPurchaseDTO(purchase);
    }

    public List<GetPurchaseDTO> list() {
        return purchaseRepository.findAll().stream().map(GetPurchaseDTO::new).toList();
    }
}
