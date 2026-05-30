package gr.antivasis.retailmanagementsystem.services;

import gr.antivasis.retailmanagementsystem.dtos.products.GetProductDTO;
import gr.antivasis.retailmanagementsystem.dtos.purchases.CreatePurchaseItemDTO;
import gr.antivasis.retailmanagementsystem.dtos.purchases.GetPurchaseDTO;
import gr.antivasis.retailmanagementsystem.entities.Customer;
import gr.antivasis.retailmanagementsystem.entities.Product;
import gr.antivasis.retailmanagementsystem.entities.Purchase;
import gr.antivasis.retailmanagementsystem.entities.PurchaseItem;
import gr.antivasis.retailmanagementsystem.repositories.CustomerRepository;
import gr.antivasis.retailmanagementsystem.repositories.ProductRepository;
import gr.antivasis.retailmanagementsystem.repositories.PurchaseItemRepository;
import gr.antivasis.retailmanagementsystem.repositories.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseItemRepository purchaseItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public List<GetPurchaseDTO> getByCustomerId(UUID customerId) {
        return purchaseRepository.findByCustomerId(customerId).stream().map(GetPurchaseDTO::new).toList();
    }

    public GetPurchaseDTO getById(UUID id) {
        return new GetPurchaseDTO(purchaseRepository.findById(id).orElseThrow());
    }

    public GetPurchaseDTO create(UUID customerId, List<CreatePurchaseItemDTO> items) {
        Customer customer = customerRepository.findById(customerId).orElseThrow();
        if(!customer.getIsActive()) {
            throw new IllegalStateException("Customer " + customerId + " is not active");
        }
        Purchase purchase = new Purchase();
        purchase.setCustomer(customer);
        purchase.setPurchasedAt(LocalDateTime.now());
        purchase = purchaseRepository.save(purchase);

        PurchaseItem item;
        for(CreatePurchaseItemDTO itemDTO : items) {
            item = new PurchaseItem(itemDTO, productRepository);
            item.setPurchase(purchase);
            purchaseItemRepository.save(item);
        }

        return new GetPurchaseDTO(purchase);
    }

    public List<GetPurchaseDTO> list() {
        return purchaseRepository.findAll().stream().map(GetPurchaseDTO::new).toList();
    }
}
