package gr.antivasis.retailmanagementsystem.services;

import gr.antivasis.retailmanagementsystem.dtos.products.CreateUpdateProductDTO;
import gr.antivasis.retailmanagementsystem.dtos.products.GetProductDTO;
import gr.antivasis.retailmanagementsystem.entities.Product;
import gr.antivasis.retailmanagementsystem.exceptions.ResourceNotFoundException;
import gr.antivasis.retailmanagementsystem.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public GetProductDTO create(CreateUpdateProductDTO productDTO) {
        Product product = new Product(productDTO);
        product.setIsActive(true);
        product = productRepository.save(product);
        return new GetProductDTO(product);
    }

    @Transactional
    public GetProductDTO update(UUID id, CreateUpdateProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product", id)
                );
        product = productRepository.save(product.fromDTO(productDTO));
        return new GetProductDTO(product);
    }

    public GetProductDTO get(UUID id) {
        return new GetProductDTO(
                productRepository
                        .findByIdAndIsActiveTrue(id)
                        .orElseThrow(
                                () -> new ResourceNotFoundException("Product", id)
                        )
        );
    }

    public List<GetProductDTO> list(String query) {
        List<Product> products;
        if (query != null && !query.isBlank()) {
            products = productRepository.findByQuery(query);
        } else {
            products = productRepository.findAllByIsActiveTrue();
        }
        return products.stream().map(GetProductDTO::new).toList();
    }

    public ResponseEntity<?> delete(UUID id) {
        Product product = productRepository
                .findByIdAndIsActiveTrue(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product", id)
                );
        product.setIsActive(false);
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }
}
