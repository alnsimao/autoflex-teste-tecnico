package com.aln.autoflex.dto;

import java.math.BigDecimal;

public record ProductMaterialDTO(Long rawMaterialId, String rawMaterialName,BigDecimal quantityNeeded) {
}
