package com.aln.autoflex.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RawMaterialResponseDTO(Long id, String name, LocalDateTime createdAt, BigDecimal stockQuantity) {
}
