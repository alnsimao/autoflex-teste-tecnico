package com.aln.autoflex.controller;

import com.aln.autoflex.dto.ProductCompositionRequestDTO;
import com.aln.autoflex.dto.ProductCompositionResponseDTO;
import com.aln.autoflex.dto.ProductionDTO;
import com.aln.autoflex.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin("*")
@RestController
@RequestMapping("/composition")
@RequiredArgsConstructor
public class ProductionController {
    private final ProductionService productionService;

    @PostMapping("/")
    public ResponseEntity<ProductCompositionResponseDTO> createProduct(@RequestBody ProductCompositionRequestDTO requestDTO) {
        ProductCompositionResponseDTO responseDTO = productionService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductCompositionResponseDTO> getProduct(@PathVariable Long id) {
        ProductCompositionResponseDTO responseDTO = productionService.findById(id);
        return ResponseEntity.ok(responseDTO);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductCompositionResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductCompositionRequestDTO requestDTO) {
        ProductCompositionResponseDTO responseDTO = productionService.update(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
    @GetMapping("/list")
    public ResponseEntity<List<ProductCompositionResponseDTO>> listByProduct(@RequestParam Long productId) {
        return ResponseEntity.ok(productionService.findAll(productId));
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productionService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/available")
    public List<ProductionDTO> getAvailable() {
        return productionService.calculateProductionAvaliable();
    }
}
