package gr.antivasis.retailmanagementsystem.controllers;

import gr.antivasis.retailmanagementsystem.dtos.products.CreateUpdateProductDTO;
import gr.antivasis.retailmanagementsystem.dtos.products.GetProductDTO;
import gr.antivasis.retailmanagementsystem.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public GetProductDTO createProduct(@RequestBody CreateUpdateProductDTO productDTO) {
        return productService.create(productDTO);
    }

    @PutMapping("/{id}")
    public GetProductDTO updateProduct(@PathVariable UUID id, CreateUpdateProductDTO productDTO){
        return productService.update(id, productDTO);
    }

    @GetMapping("/{id}")
    public GetProductDTO getProduct(@PathVariable UUID id){
        return productService.get(id);
    }

    @GetMapping
    public List<GetProductDTO> listProducts(@RequestParam(required = false) String query){
        return productService.list(query);
    }
}
