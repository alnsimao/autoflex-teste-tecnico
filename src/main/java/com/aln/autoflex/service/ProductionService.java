package com.aln.autoflex.service;


import com.aln.autoflex.dto.ProductCompositionRequestDTO;
import com.aln.autoflex.dto.ProductCompositionResponseDTO;
import com.aln.autoflex.dto.ProductionDTO;
import com.aln.autoflex.exceptions.ResourceNotFoundException;
import com.aln.autoflex.model.Product;
import com.aln.autoflex.model.ProductComposition;
import com.aln.autoflex.model.RawMaterial;
import com.aln.autoflex.repository.ProductCompositionRepository;
import com.aln.autoflex.repository.ProductRepository;
import com.aln.autoflex.repository.RawMaterialRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductionService {
    private final ProductRepository productRepository;
    private final RawMaterialRepository materialRepository;
    private final ProductCompositionRepository compositionRepository;

    @Transactional
    public ProductCompositionResponseDTO create(ProductCompositionRequestDTO productCompositionRequestDTO) {

        Product product = productRepository.findById(productCompositionRequestDTO.productId()).orElseThrow
                (
                        ()-> new ResourceNotFoundException("Product not found")
                );

        RawMaterial material = materialRepository.findById(productCompositionRequestDTO.rawMaterialId()).orElseThrow
                        (
                                ()-> new ResourceNotFoundException("Material not found")
                        );
        if(productCompositionRequestDTO.quantityNeeded()
                .compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity needed must be greater than 0");
        }

        ProductComposition productComposition = new ProductComposition();
        productComposition.setProduct(product);
        productComposition.setRawMaterial(material);
        productComposition.setQuantityNeeded(productCompositionRequestDTO.quantityNeeded());

        compositionRepository.save(productComposition);
        return toDTO(productComposition);

    }
    @Transactional
    public ProductCompositionResponseDTO update(Long id, ProductCompositionRequestDTO productCompositionRequestDTO) {
        ProductComposition productComposition = compositionRepository.findById(id).orElseThrow
                (
                        ()-> new ResourceNotFoundException("Product composition not found")
                );
        Product product = productRepository.findById(productCompositionRequestDTO.productId()).orElseThrow
                (
                        ()-> new ResourceNotFoundException("Product not found")
                );
        RawMaterial rawMaterial = materialRepository.findById(productCompositionRequestDTO.rawMaterialId())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Material not found")
                );
        if(productCompositionRequestDTO.quantityNeeded()
        .compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantity needed must be greater than 0");
        }
        productComposition.setProduct(product);
        productComposition.setRawMaterial(rawMaterial);
        productComposition.setQuantityNeeded(productCompositionRequestDTO.quantityNeeded());

        return toDTO(productComposition);
    }
    @Transactional
    public void delete(Long id) {
            ProductComposition productComposition = compositionRepository.findById(id).orElseThrow
                    (
                            ()-> new ResourceNotFoundException("Product composition not found")
                    );
            compositionRepository.delete(productComposition);
    }

    @Transactional
    public ProductCompositionResponseDTO findById(Long id) {
        ProductComposition productComposition = compositionRepository.findById(id).orElseThrow
                (()-> new ResourceNotFoundException("Product composition not found"));

        return toDTO(productComposition);
    }

    @Transactional
    public List<ProductCompositionResponseDTO> findByProductId(Long productId) {
        return compositionRepository.findAllByProductId(productId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public List<ProductionDTO> calculateProductionAvaliable(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::calculateProduct)
                .toList();

    }

    private ProductionDTO calculateProduct(Product product) {

        List<ProductComposition> compositions =
                compositionRepository.findAllByProductId(product.getId());

        if (compositions.isEmpty()) {
            return new ProductionDTO(
                    product.getId(),
                    product.getName(),
                    BigDecimal.ZERO
            );
        }

        BigDecimal minProduction = compositions.stream()
                .map(comp -> {
                    BigDecimal stock = comp.getRawMaterial().getStockQuantity();
                    BigDecimal needed = comp.getQuantityNeeded();

                    return stock.divide(needed, 0, RoundingMode.DOWN);
                })
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        return new ProductionDTO(
                product.getId(),
                product.getName(),
                minProduction
        );
    }
    private ProductCompositionResponseDTO toDTO(ProductComposition productComposition) {
        return new ProductCompositionResponseDTO(
                productComposition.getId(),
                productComposition.getProduct().getId(),
                productComposition.getProduct().getName(),
                productComposition.getRawMaterial().getId(),
                productComposition.getRawMaterial().getName(),
                productComposition.getQuantityNeeded()
        );



    }

}
