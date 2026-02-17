package com.sm.jeyz9.storemateapi.repository;

import com.sm.jeyz9.storemateapi.models.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
    @Query(value = """
        SELECT SUM(ps.stock_quantity) FROM product_stocks ps WHERE ps.product_id = :productId;
    """, nativeQuery = true)
    Integer findStockQuantityByProductId(@Param("productId") Long productId);
}
