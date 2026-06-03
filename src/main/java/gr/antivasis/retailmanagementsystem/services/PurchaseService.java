package gr.antivasis.retailmanagementsystem.services;

import gr.antivasis.retailmanagementsystem.dtos.purchases.CreatePurchaseItemDTO;
import gr.antivasis.retailmanagementsystem.dtos.purchases.GetPurchaseDTO;
import gr.antivasis.retailmanagementsystem.entities.Customer;
import gr.antivasis.retailmanagementsystem.entities.PointsBatch;
import gr.antivasis.retailmanagementsystem.entities.Purchase;
import gr.antivasis.retailmanagementsystem.entities.PurchaseItem;
import gr.antivasis.retailmanagementsystem.enums.CustomerTier;
import gr.antivasis.retailmanagementsystem.enums.PointsBatchStatus;
import gr.antivasis.retailmanagementsystem.exceptions.ActionNotAllowedException;
import gr.antivasis.retailmanagementsystem.exceptions.ResourceNotFoundException;
import gr.antivasis.retailmanagementsystem.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
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
    private final PointsBatchRepository pointsBatchRepository;

    public GetPurchaseDTO getById(UUID id) {
        return new GetPurchaseDTO(purchaseRepository.findById(id).orElseThrow());
    }

    private int redeemPoints(Customer customer, int redeemedPoints) {
//        List<PointsBatch> pointsBatches = pointsBatchRepository.findByCustomerIdAndStatus(customerId, PointsBatchStatus.REDEEMABLE);
        List<PointsBatch> pointsBatches = customer.getPointsBatches().stream().filter(p -> p.getStatus() == PointsBatchStatus.REDEEMABLE).toList();
        if (pointsBatches.isEmpty()) {
            throw new ActionNotAllowedException("No redeemable points batches found for the customer.");
        }

        if (customer.getPoints() < 20) {
            throw new ActionNotAllowedException("Customer does not have enough points to redeem.");
        }

        if (customer.getPoints() < redeemedPoints) {
            throw new ActionNotAllowedException("Insufficient points in the redeemable points batch.");
        }

        if(redeemedPoints % 100 != 0) {
            throw new ActionNotAllowedException("Redeemed points must be a multiple of 100.");
        }

        int points = redeemedPoints;

        for (PointsBatch pointsBatch : pointsBatches) {
            if (pointsBatch.getPoints() <= points) {
                points -= pointsBatch.getPoints();
                pointsBatch.setPoints(0);
                pointsBatch.setStatus(PointsBatchStatus.REDEEMED);
            } else {
                pointsBatch.setPoints(pointsBatch.getPoints() - points);
                points = 0;
            }

            pointsBatchRepository.save(pointsBatch);

            if (points == 0){
                break;
            }
        }

        assert points == 0;
        return (redeemedPoints % 100) * 10;
    }

    @Transactional
    public GetPurchaseDTO create(UUID customerId, List<CreatePurchaseItemDTO> items, int redeemedPoints) {
        Customer customer = customerRepository
                .findByIdAndIsActiveTrue(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", customerId)
                );

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

        int discount = 0;
        if (redeemedPoints > 0) {
            discount = redeemPoints(customer, redeemedPoints);
        }

        int totalAmount = purchase.getTotalAmount() - discount;

        CustomerTier customerTier = CustomerTier.fromPoints(customer.getLifetimePoints());
        double points = customerTier.pointsMultiplier * (totalAmount % 10);
        PointsBatch pointsBatch = new PointsBatch(customer, points);

        pointsBatchRepository.save(pointsBatch);

        customer.setLifetimePoints(customer.getLifetimePoints() + (int) points);
        customerRepository.save(customer);

        return new GetPurchaseDTO(purchase);
    }

    public List<GetPurchaseDTO> list() {
        return purchaseRepository.findAll().stream().map(GetPurchaseDTO::new).toList();
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void expirePointBatches(){
        List<PointsBatch> expiredBatches = pointsBatchRepository.findByStatusIsNot(PointsBatchStatus.EXPIRED)
                .stream()
                .filter(p -> p.getCreatedAt().plusYears(1).isBefore(LocalDateTime.now()))
                .toList();
        expiredBatches.forEach(p -> p.setStatus(PointsBatchStatus.EXPIRED));
        pointsBatchRepository.saveAll(expiredBatches);
    }
}
