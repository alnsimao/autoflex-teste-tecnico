package com.aln.autoflex.controller;


import com.aln.autoflex.dto.ProductRequestDTO;
import com.aln.autoflex.dto.ProductResponseDTO;
import com.aln.autoflex.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/")
    @Transactional
    public ResponseEntity<ProductResponseDTO> addProduct(@RequestBody ProductRequestDTO product) {
        ProductResponseDTO responseDTO = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable Long id) {
        ProductResponseDTO responseDTO = productService.getProduct(id);
        return ResponseEntity.ok(responseDTO);

    }
    @GetMapping("/list")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        List<ProductResponseDTO> responseDTO = productService.getAllProducts();
        return ResponseEntity.ok(responseDTO);
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO product) {
        ProductResponseDTO responseDTO = productService.updateProduct(id, product);
        return ResponseEntity.ok(responseDTO);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
         productService.deleteProduct(id);
         return ResponseEntity.noContent().build();
    }

}
