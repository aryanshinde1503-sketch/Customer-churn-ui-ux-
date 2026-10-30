package com.churnprediction.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.churnprediction.app.dto.PredictionDto;
import com.churnprediction.app.dto.PredictionFormDto;
import com.churnprediction.app.entity.Prediction;

/**
 * Service interface for Prediction operations.
 */
public interface PredictionService {

    PredictionDto predict(PredictionFormDto form, Long customerId);

    Prediction savePrediction(PredictionDto dto, Long customerId);

    Optional<Prediction> findById(Long id);

    Page<Prediction> searchAndFilter(String search, Prediction.RiskCategory riskCategory, Pageable pageable);

    List<Prediction> findByCustomerId(Long customerId);

    List<PredictionDto> findRecentPredictions(int limit);

    long countTotal();

    long countByRiskCategory(Prediction.RiskCategory category);
}
