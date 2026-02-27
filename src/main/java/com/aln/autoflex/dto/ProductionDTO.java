package com.aln.autoflex.dto;

import java.math.BigDecimal;

public record ProductionDTO(
        Long productId,
        String productName,
        BigDecimal maxQuantityPossible
) {
}
