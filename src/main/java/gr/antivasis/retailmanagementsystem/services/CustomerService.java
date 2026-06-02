package gr.antivasis.retailmanagementsystem.services;

import gr.antivasis.retailmanagementsystem.dtos.customers.CreateUpdateCustomerDTO;
import gr.antivasis.retailmanagementsystem.dtos.customers.GetCustomerDTO;
import gr.antivasis.retailmanagementsystem.dtos.purchases.GetPurchaseDTO;
import gr.antivasis.retailmanagementsystem.entities.Customer;
import gr.antivasis.retailmanagementsystem.entities.PointsBatch;
import gr.antivasis.retailmanagementsystem.enums.PointsBatchStatus;
import gr.antivasis.retailmanagementsystem.exceptions.ActionNotAllowedException;
import gr.antivasis.retailmanagementsystem.exceptions.ResourceNotFoundException;
import gr.antivasis.retailmanagementsystem.repositories.CustomerRepository;
import gr.antivasis.retailmanagementsystem.repositories.PointsBatchRepository;
import gr.antivasis.retailmanagementsystem.repositories.PurchaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PurchaseRepository purchaseRepository;
    private final PointsBatchRepository pointsBatchRepository;

    @Transactional
    public GetCustomerDTO create(CreateUpdateCustomerDTO customerDTO) {
//        customerRepository.findByEmailAndIsActiveTrue(customerDTO.email())
//                .orElseThrow(
//                        () -> new ResourceNotFoundException("Customer", customerDTO.email())
//                );
        Customer customer = new Customer(customerDTO);
        customer.setIsActive(true);
        customer.setLifetimePoints(0);
        customer = customerRepository.save(customer);
        return new GetCustomerDTO(customer);
    }

    @Transactional
    public GetCustomerDTO update(UUID id, CreateUpdateCustomerDTO customerDTO) {
        Customer customer = customerRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", id)
                );
        customer = customerRepository.save(customer.fromDTO(customerDTO));
        return new GetCustomerDTO(customer);
    }

    public GetCustomerDTO get(UUID id) {
        return new GetCustomerDTO(
                customerRepository
                        .findByIdAndIsActiveTrue(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Customer", id)
                        )
        );
    }

    public int redeemPoints(UUID customerId, int redeemedPoints) {
//        List<PointsBatch> pointsBatches = pointsBatchRepository.findByCustomerIdAndStatus(customerId, PointsBatchStatus.REDEEMABLE);
        Customer customer = customerRepository
                .findByIdAndIsActiveTrue(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", customerId)
                );
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

    public List<GetPurchaseDTO> getCustomerPurchases(UUID customerId) {
        customerRepository.findByIdAndIsActiveTrue(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", customerId)
                );
        return purchaseRepository.findByCustomerId(customerId).stream().map(GetPurchaseDTO::new).toList();
    }

    public List<GetCustomerDTO> list(String query) {
        List<Customer> customers;
        if (query != null && !query.isBlank()) {
            customers = customerRepository.findByQuery(query);
        } else {
            customers = customerRepository.findAllByIsActiveTrue();
        }
        return customers.stream().map(GetCustomerDTO::new).toList();
    }

    public ResponseEntity<?> delete(UUID id) {
        Customer customer = customerRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer", id)
                );
        customer.setIsActive(false);
        customerRepository.save(customer);
        return ResponseEntity.ok().build();
    }
}
