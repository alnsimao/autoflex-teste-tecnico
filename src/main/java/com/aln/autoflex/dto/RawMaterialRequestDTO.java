package com.aln.autoflex.dto;

import java.math.BigDecimal;

public record RawMaterialRequestDTO(String name, BigDecimal stockQuantity) {
}
