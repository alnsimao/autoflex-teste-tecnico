package com.aln.autoflex.controller;

import com.aln.autoflex.dto.ProductResponseDTO;
import com.aln.autoflex.dto.RawMaterialRequestDTO;
import com.aln.autoflex.dto.RawMaterialResponseDTO;
import com.aln.autoflex.service.RawMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/materials")
@RequiredArgsConstructor
public class RawMaterialController {
    private final RawMaterialService rawMaterialService;

   @PostMapping("/")
    public ResponseEntity<RawMaterialResponseDTO> addRawMaterial(@RequestBody RawMaterialRequestDTO materialRequest) {
       RawMaterialResponseDTO materialResponse = rawMaterialService.createRawMaterial(materialRequest);
       return ResponseEntity.ok(materialResponse); }

    @GetMapping("/{id}")
    public ResponseEntity<RawMaterialResponseDTO> getMaterial(@PathVariable Long id) {
       RawMaterialResponseDTO materialResponse = rawMaterialService.findRawMaterialById(id);
       return ResponseEntity.ok(materialResponse);
    }
    @GetMapping("/list")
    public ResponseEntity<List<RawMaterialResponseDTO>> getAllRawMaterials() {
       List<RawMaterialResponseDTO> materialResponse = rawMaterialService.findAllRawMaterials();
       return ResponseEntity.ok(materialResponse);
    }

    @PutMapping ("/{id}")
    public ResponseEntity<RawMaterialResponseDTO> updateRawMaterial(@PathVariable Long id, @RequestBody RawMaterialRequestDTO materialRequest) {
       RawMaterialResponseDTO materialResponse = rawMaterialService.updateRawMaterial(id, materialRequest);
       return ResponseEntity.ok(materialResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRawMaterial(@PathVariable Long id) {
       rawMaterialService.deleteRawMaterial(id);
       return ResponseEntity.noContent().build();

    }
}
