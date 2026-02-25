package com.aln.autoflex.dto;

import java.math.BigDecimal;

public record ProductMaterialDTO(Long rawMaterialId, BigDecimal quantityNeeded) {
}
