package gr.antivasis.retailmanagementsystem.controllers;

import gr.antivasis.retailmanagementsystem.dtos.customers.CreateUpdateCustomerDTO;
import gr.antivasis.retailmanagementsystem.dtos.customers.GetCustomerDTO;
import gr.antivasis.retailmanagementsystem.dtos.purchases.GetPurchaseDTO;
import gr.antivasis.retailmanagementsystem.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public GetCustomerDTO createCustomer(@RequestBody CreateUpdateCustomerDTO customerDTO) {
        return customerService.create(customerDTO);
    }

    @PutMapping("/{id}")
    public GetCustomerDTO updateCustomer(@PathVariable UUID id, @RequestBody CreateUpdateCustomerDTO customerDTO){
        return customerService.update(id, customerDTO);
    }

    @GetMapping("/{id}")
    public GetCustomerDTO getCustomer(@PathVariable UUID id){
        return customerService.get(id);
    }

    @GetMapping("/{id}/purchases")
    public List<GetPurchaseDTO> getCustomerPurchases(@PathVariable UUID id){
        return customerService.getCustomerPurchases(id);
    }

    @GetMapping
    public List<GetCustomerDTO> listCustomers(@RequestParam(required = false) String query){
        return customerService.list(query);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> softDeleteCustomer(@PathVariable UUID id){
        return customerService.delete(id);
    }
}
