package com.churnprediction.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.churnprediction.app.dto.DashboardStatsDto;
import com.churnprediction.app.dto.PredictionDto;
import com.churnprediction.app.entity.Customer;
import com.churnprediction.app.entity.Prediction;
import com.churnprediction.app.repository.CustomerRepository;
import com.churnprediction.app.repository.PredictionRepository;

/**
 * Service for dashboard statistics aggregation.
 */
@Service
@Transactional(readOnly = true)
public class DashboardService {

    private static final Logger log = LoggerFactory.getLogger(DashboardService.class);

    private final CustomerRepository customerRepository;
    private final PredictionRepository predictionRepository;
    private final PredictionService predictionService;

    public DashboardService(CustomerRepository customerRepository,
                            PredictionRepository predictionRepository,
                            PredictionService predictionService) {
        this.customerRepository = customerRepository;
        this.predictionRepository = predictionRepository;
        this.predictionService = predictionService;
    }

    public DashboardStatsDto getDashboardStats() {
        long total = customerRepository.count();
        long churned = customerRepository.countByChurnStatus(true);
        long active = total - churned;
        double churnRate = total > 0 ? (double) churned / total : 0.0;
        long highRisk = customerRepository.countHighRiskCustomers();
        Double avgTenure = customerRepository.findAverageTenure();
        Double avgCharges = customerRepository.findAverageMonthlyCharges();

        long totalPredictions = predictionRepository.count();
        long highRiskPred = predictionRepository.countByRiskCategory(Prediction.RiskCategory.HIGH);
        long mediumRiskPred = predictionRepository.countByRiskCategory(Prediction.RiskCategory.MEDIUM);
        long lowRiskPred = predictionRepository.countByRiskCategory(Prediction.RiskCategory.LOW);

        return DashboardStatsDto.builder()
            .totalCustomers(total)
            .activeCustomers(active)
            .churnedCustomers(churned)
            .churnRate(churnRate)
            .highRiskCustomers(highRisk)
            .averageTenure(avgTenure != null ? avgTenure : 0.0)
            .averageMonthlyCharges(avgCharges != null ? avgCharges : 0.0)
            .totalPredictions(totalPredictions)
            .highRiskPredictions(highRiskPred)
            .mediumRiskPredictions(mediumRiskPred)
            .lowRiskPredictions(lowRiskPred)
            .build();
    }

    public List<Customer> getRecentCustomers() {
        return customerRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public List<PredictionDto> getRecentPredictions() {
        return predictionService.findRecentPredictions(8);
    }
}
