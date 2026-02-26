package com.aln.autoflex.service;


import com.aln.autoflex.dto.RawMaterialRequestDTO;
import com.aln.autoflex.dto.RawMaterialResponseDTO;
import com.aln.autoflex.exceptions.ResourceNotFoundException;
import com.aln.autoflex.model.RawMaterial;
import com.aln.autoflex.repository.RawMaterialRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;




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
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Raw Material not found"));
        rawMaterial.setName(rawMaterialRequestDTO.name());
        rawMaterial.setStockQuantity(rawMaterialRequestDTO.stockQuantity());
        return toDTO(rawMaterial);
    }
    @Transactional
    public void deleteRawMaterial(Long id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Raw Material not found"));
        rawMaterialRepository.delete(rawMaterial);
    }

    public RawMaterialResponseDTO findRawMaterialById(Long id) {
        RawMaterial material = rawMaterialRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Raw Material not found"));
        return toDTO(material);
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
