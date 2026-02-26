package com.aln.autoflex.dto;

import java.math.BigDecimal;

public record ProductCompositionRequestDTO(
        Long productId,
        Long rawMaterialId,
        BigDecimal quantityNeeded
) {
}
