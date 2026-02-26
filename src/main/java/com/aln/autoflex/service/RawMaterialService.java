package com.aln.autoflex.service;


import com.aln.autoflex.dto.RawMaterialRequestDTO;
import com.aln.autoflex.dto.RawMaterialResponseDTO;
import com.aln.autoflex.model.RawMaterial;
import com.aln.autoflex.repository.RawMaterialRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RawMaterialService {
    private final RawMaterialRepository rawMaterialRepository;

    @Transactional
    public RawMaterialResponseDTO createRawMaterial(RawMaterialRequestDTO rawMaterialRequestDTO) {
        RawMaterial material = new RawMaterial();
        material.setName(rawMaterialRequestDTO.name());
        material.setStockQuantity(rawMaterialRequestDTO.stockQuantity());
        RawMaterial savedRawMaterial = rawMaterialRepository.save(material);

        return toDTO(savedRawMaterial);

    }

    @Transactional
    public RawMaterialResponseDTO updateRawMaterial(Long id,RawMaterialRequestDTO rawMaterialRequestDTO) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new RuntimeException("Raw Material not found"));
        rawMaterial.setName(rawMaterialRequestDTO.name());
        rawMaterial.setStockQuantity(rawMaterialRequestDTO.stockQuantity());
        RawMaterial updatedRawMaterial = rawMaterialRepository.save(rawMaterial);
        return toDTO(updatedRawMaterial);
    }
    @Transactional
    public void deleteRawMaterial(Long id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new RuntimeException("Raw Material not found"));
        rawMaterialRepository.delete(rawMaterial);
    }

    public RawMaterialResponseDTO findRawMaterialById(Long id) {
        Optional<RawMaterial> rawMaterial = rawMaterialRepository.findById(id);
        if (rawMaterial.isPresent()) {
            return toDTO(rawMaterial.get());
        } else {
            throw new RuntimeException("Raw Material not found");
        }


    }
    private RawMaterialResponseDTO toDTO(RawMaterial rawMaterial) {
        return new RawMaterialResponseDTO(
                rawMaterial.getId(),
                rawMaterial.getName(),
                rawMaterial.getCreatedAt(),
                rawMaterial.getStockQuantity()
        );
    }
}
