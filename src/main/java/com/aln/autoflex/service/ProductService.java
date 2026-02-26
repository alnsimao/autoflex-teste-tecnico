package com.aln.autoflex.service;

import com.aln.autoflex.dto.ProductRequestDTO;
import com.aln.autoflex.dto.ProductResponseDTO;
import com.aln.autoflex.exceptions.ResourceNotFoundException;
import com.aln.autoflex.model.Product;
import com.aln.autoflex.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.name());
        product.setPrice(productRequestDTO.price());

        Product savedProduct = productRepository.save(product);

        return toDTO(savedProduct);
    }
    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
        product.setName(productRequestDTO.name());
        product.setPrice(productRequestDTO.price());

        return toDTO(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
        productRepository.delete(product);
    }
    @Transactional
    public ProductResponseDTO getProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));
        return toDTO(product);
    }
    @Transactional
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();

    }


    private ProductResponseDTO toDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice()
               );
    }
}
