package gr.antivasis.retailmanagementsystem.services;

import gr.antivasis.retailmanagementsystem.dtos.customers.CreateUpdateCustomerDTO;
import gr.antivasis.retailmanagementsystem.dtos.customers.GetCustomerDTO;
import gr.antivasis.retailmanagementsystem.entities.Customer;
import gr.antivasis.retailmanagementsystem.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public GetCustomerDTO create(CreateUpdateCustomerDTO customerDTO) {
        Customer customer = new Customer(customerDTO);
        customer = customerRepository.save(customer);
        return new GetCustomerDTO(customer);
    }

    public GetCustomerDTO update(UUID id, CreateUpdateCustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow();
        customer = customerRepository.save(customer.fromDTO(customerDTO));
        return new GetCustomerDTO(customer);
    }

    public GetCustomerDTO get(UUID id) {
        return new GetCustomerDTO(customerRepository.findById(id).orElseThrow());
    }

    public List<GetCustomerDTO> list(String query) {
        List<Customer> customers;
        if (query != null && !query.isBlank()) {
            customers = customerRepository.findByQuery(query);
        } else {
            customers = customerRepository.findAll();
        }
        return customers.stream().map(GetCustomerDTO::new).toList();
    }

}
