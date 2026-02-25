package com.aln.autoflex.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponseDTO(Long id,String name, BigDecimal price, List<ProductMaterialDTO> materialList) {
}
