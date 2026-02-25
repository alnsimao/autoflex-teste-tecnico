package com.aln.autoflex.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequestDTO(String name, BigDecimal price, List<ProductMaterialDTO> materialDTOList) {
}
