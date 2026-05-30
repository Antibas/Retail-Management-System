package gr.antivasis.retailmanagementsystem.controllers;

import gr.antivasis.retailmanagementsystem.dtos.purchases.CreatePurchaseItemDTO;
import gr.antivasis.retailmanagementsystem.dtos.purchases.GetPurchaseDTO;
import gr.antivasis.retailmanagementsystem.services.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @PostMapping("/customer/{customerId}")
    public GetPurchaseDTO createPurchase(@PathVariable UUID customerId, @RequestBody List<CreatePurchaseItemDTO> items) {
        return purchaseService.create(customerId, items);
    }

    @GetMapping("/{id}")
    public GetPurchaseDTO getPurchaseById(@PathVariable UUID id){
        return purchaseService.getById(id);
    }

    @GetMapping("/customer/{id}")
    public GetPurchaseDTO getPurchaseByCustomerId(@PathVariable UUID id){
        return purchaseService.getById(id);
    }

    @GetMapping
    public List<GetPurchaseDTO> listPurchases(){
        return purchaseService.list();
    }
}
