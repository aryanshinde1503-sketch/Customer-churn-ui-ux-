package com.churnprediction.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.churnprediction.app.entity.Prediction;

/**
 * Repository for Prediction data access operations.
 */
@Repository
public interface PredictionRepository extends JpaRepository<Prediction, Long> {

    List<Prediction> findByCustomerId(Long customerId);

    Optional<Prediction> findTopByCustomerIdOrderByCreatedAtDesc(Long customerId);

    long countByRiskCategory(Prediction.RiskCategory riskCategory);

    long countByPredictionMethod(Prediction.PredictionMethod method);

    // Paginated search
    @Query("SELECT p FROM Prediction p JOIN p.customer c WHERE " +
           "(:search IS NULL OR :search = '' OR " +
           "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.customerId) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:riskCategory IS NULL OR p.riskCategory = :riskCategory)")
    Page<Prediction> searchAndFilter(
        @Param("search") String search,
        @Param("riskCategory") Prediction.RiskCategory riskCategory,
        Pageable pageable
    );

    // Risk distribution for analytics
    @Query("SELECT p.riskCategory, COUNT(p) FROM Prediction p GROUP BY p.riskCategory")
    List<Object[]> countByRiskCategoryGrouped();

    // Recent predictions for dashboard
    @Query("SELECT p FROM Prediction p ORDER BY p.createdAt DESC")
    List<Prediction> findTop10ByOrderByCreatedAtDesc(Pageable pageable);

    // Total predictions
    @Query("SELECT COUNT(p) FROM Prediction p")
    long countTotal();
}
