package com.churnprediction.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.churnprediction.app.dto.PredictionFormDto;
import com.churnprediction.app.entity.Customer;
import com.churnprediction.app.entity.Prediction;

/**
 * Demo Prediction Service implementing a transparent rule-based churn scoring engine.
 *
 * ⚠️ IMPORTANT: This service uses simplified business rules for DEMONSTRATION purposes only.
 * Results are ESTIMATION ONLY and should NOT be treated as actual ML model predictions.
 *
 * This service is deliberately isolated so it can be replaced with real ML integration
 * (XGBoost via Python/FastAPI) in a future version without touching other components.
 *
 * Scoring logic:
 * - Contract type (largest weight): Month-to-Month customers churn far more
 * - Tenure: Longer tenure = lower churn risk
 * - Internet Service: Fiber optic customers churn more
 * - Payment Method: Electronic check correlates with higher churn
 * - Support services: Lack of tech support, security, backup increases risk
 * - Senior citizen: Slight elevated risk
 * - Monthly charges: Higher charges = slightly elevated risk
 */
@Service
public class DemoPredictionService {

    private static final Logger log = LoggerFactory.getLogger(DemoPredictionService.class);

    private static final double LOW_THRESHOLD = 0.40;
    private static final double HIGH_THRESHOLD = 0.70;

    // Score weights (must sum to ~1.0 for normalized output)
    private static final double WEIGHT_CONTRACT = 0.28;
    private static final double WEIGHT_TENURE = 0.18;
    private static final double WEIGHT_INTERNET = 0.14;
    private static final double WEIGHT_PAYMENT = 0.10;
    private static final double WEIGHT_SUPPORT = 0.14;
    private static final double WEIGHT_CHARGES = 0.08;
    private static final double WEIGHT_DEMOGRAPHICS = 0.08;

    /**
     * Computes a demonstration churn probability from customer features.
     * Returns a value between 0.0 (no churn risk) and 1.0 (certain churn).
     *
     * @param form the prediction form containing customer features
     * @return churn probability [0.0, 1.0]
     */
    public double computeChurnProbability(PredictionFormDto form) {
        log.debug("Computing demo churn probability for form data");

        double score = 0.0;
        score += scoreContract(form.getContract());
        score += scoreTenure(form.getTenure());
        score += scoreInternetService(form.getInternetService());
        score += scorePaymentMethod(form.getPaymentMethod());
        score += scoreSupportServices(form);
        score += scoreMonthlyCharges(form.getMonthlyCharges());
        score += scoreDemographics(form);

        // Clamp to [0.0, 1.0]
        double probability = Math.max(0.0, Math.min(1.0, score));
        log.debug("Demo churn probability computed: {}", probability);
        return probability;
    }

    public double computeChurnProbabilityFromCustomer(Customer customer) {
        PredictionFormDto form = new PredictionFormDto();
        form.setGender(customer.getGender());
        form.setSeniorCitizen(customer.getSeniorCitizen());
        form.setPartner(customer.getPartner());
        form.setDependents(customer.getDependents());
        form.setTenure(customer.getTenure());
        form.setPhoneService(customer.getPhoneService());
        form.setMultipleLines(customer.getMultipleLines());
        form.setInternetService(customer.getInternetService());
        form.setOnlineSecurity(customer.getOnlineSecurity());
        form.setOnlineBackup(customer.getOnlineBackup());
        form.setDeviceProtection(customer.getDeviceProtection());
        form.setTechSupport(customer.getTechSupport());
        form.setStreamingTv(customer.getStreamingTv());
        form.setStreamingMovies(customer.getStreamingMovies());
        form.setContract(customer.getContract());
        form.setPaperlessBilling(customer.getPaperlessBilling());
        form.setPaymentMethod(customer.getPaymentMethod());
        form.setMonthlyCharges(customer.getMonthlyCharges());
        form.setTotalCharges(customer.getTotalCharges());
        return computeChurnProbability(form);
    }

    public Prediction.RiskCategory categorize(double probability) {
        if (probability >= HIGH_THRESHOLD) return Prediction.RiskCategory.HIGH;
        if (probability >= LOW_THRESHOLD) return Prediction.RiskCategory.MEDIUM;
        return Prediction.RiskCategory.LOW;
    }

    public BigDecimal roundedProbability(double probability) {
        return BigDecimal.valueOf(probability).setScale(4, RoundingMode.HALF_UP);
    }

    // --- Individual scoring methods ---

    private double scoreContract(Customer.Contract contract) {
        if (contract == null) return WEIGHT_CONTRACT * 0.5;
        return switch (contract) {
            case MONTH_TO_MONTH -> WEIGHT_CONTRACT * 1.0;  // Highest churn
            case ONE_YEAR -> WEIGHT_CONTRACT * 0.35;
            case TWO_YEAR -> WEIGHT_CONTRACT * 0.05;       // Lowest churn
        };
    }

    private double scoreTenure(Integer tenure) {
        if (tenure == null) return WEIGHT_TENURE * 0.5;
        // Longer tenure = lower churn probability
        if (tenure <= 6) return WEIGHT_TENURE * 1.0;
        if (tenure <= 12) return WEIGHT_TENURE * 0.85;
        if (tenure <= 24) return WEIGHT_TENURE * 0.60;
        if (tenure <= 36) return WEIGHT_TENURE * 0.40;
        if (tenure <= 48) return WEIGHT_TENURE * 0.25;
        if (tenure <= 60) return WEIGHT_TENURE * 0.15;
        return WEIGHT_TENURE * 0.05;
    }

    private double scoreInternetService(Customer.InternetService internetService) {
        if (internetService == null) return WEIGHT_INTERNET * 0.5;
        return switch (internetService) {
            case FIBER_OPTIC -> WEIGHT_INTERNET * 1.0;  // Highest churn for fiber
            case DSL -> WEIGHT_INTERNET * 0.45;
            case NO -> WEIGHT_INTERNET * 0.10;
        };
    }

    private double scorePaymentMethod(Customer.PaymentMethod paymentMethod) {
        if (paymentMethod == null) return WEIGHT_PAYMENT * 0.5;
        return switch (paymentMethod) {
            case ELECTRONIC_CHECK -> WEIGHT_PAYMENT * 1.0;  // Highest churn
            case MAILED_CHECK -> WEIGHT_PAYMENT * 0.55;
            case BANK_TRANSFER -> WEIGHT_PAYMENT * 0.25;
            case CREDIT_CARD -> WEIGHT_PAYMENT * 0.20;
        };
    }

    private double scoreSupportServices(PredictionFormDto form) {
        double risk = 0.0;
        double perService = WEIGHT_SUPPORT / 4.0;

        // No tech support increases churn risk
        if (form.getTechSupport() == Customer.ServiceStatus.NO) risk += perService;
        // No online security increases churn risk
        if (form.getOnlineSecurity() == Customer.ServiceStatus.NO) risk += perService;
        // No online backup increases churn risk
        if (form.getOnlineBackup() == Customer.ServiceStatus.NO) risk += perService;
        // No device protection increases churn risk
        if (form.getDeviceProtection() == Customer.ServiceStatus.NO) risk += perService;

        return risk;
    }

    private double scoreMonthlyCharges(BigDecimal monthlyCharges) {
        if (monthlyCharges == null) return WEIGHT_CHARGES * 0.5;
        double charges = monthlyCharges.doubleValue();
        // Normalize: max ~$120/month
        double normalized = Math.min(charges / 120.0, 1.0);
        return WEIGHT_CHARGES * normalized;
    }

    private double scoreDemographics(PredictionFormDto form) {
        double risk = 0.0;
        // Senior citizen: slight elevated risk
        if (Boolean.TRUE.equals(form.getSeniorCitizen())) {
            risk += WEIGHT_DEMOGRAPHICS * 0.5;
        }
        // No partner: slight elevated risk
        if (Boolean.FALSE.equals(form.getPartner())) {
            risk += WEIGHT_DEMOGRAPHICS * 0.3;
        }
        // No dependents: slight elevated risk
        if (Boolean.FALSE.equals(form.getDependents())) {
            risk += WEIGHT_DEMOGRAPHICS * 0.2;
        }
        return Math.min(risk, WEIGHT_DEMOGRAPHICS);
    }
}
