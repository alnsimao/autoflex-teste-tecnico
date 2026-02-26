package com.aln.autoflex.repository;

import com.aln.autoflex.dto.ProductCompositionResponseDTO;
import com.aln.autoflex.model.ProductComposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductCompositionRepository extends JpaRepository<ProductComposition, Long> {
    List<ProductComposition> findAllByProductId(Long id);

}
