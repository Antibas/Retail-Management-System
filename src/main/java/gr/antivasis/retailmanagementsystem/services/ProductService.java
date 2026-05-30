package gr.antivasis.retailmanagementsystem.services;

import gr.antivasis.retailmanagementsystem.dtos.products.CreateUpdateProductDTO;
import gr.antivasis.retailmanagementsystem.dtos.products.GetProductDTO;
import gr.antivasis.retailmanagementsystem.entities.Product;
import gr.antivasis.retailmanagementsystem.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public GetProductDTO create(CreateUpdateProductDTO productDTO) {
        Product product = new Product(productDTO);
        product = productRepository.save(product);
        return new GetProductDTO(product);
    }

    public GetProductDTO update(UUID id, CreateUpdateProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow();
        product = productRepository.save(product.fromDTO(productDTO));
        return new GetProductDTO(product);
    }

    public GetProductDTO get(UUID id) {
        return new GetProductDTO(productRepository.findById(id).orElseThrow());
    }

    public List<GetProductDTO> list(String query) {
        List<Product> products;
        if (query != null && !query.isBlank()) {
            products = productRepository.findByQuery(query);
        } else {
            products = productRepository.findAll();
        }
        return products.stream().map(GetProductDTO::new).toList();
    }
}
