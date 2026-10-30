package com.churnprediction.app;

import com.churnprediction.app.dto.PredictionFormDto;
import com.churnprediction.app.entity.Customer;
import com.churnprediction.app.entity.Prediction;
import com.churnprediction.app.service.DemoPredictionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DemoPredictionServiceTest {

    private DemoPredictionService service;

    @BeforeEach
    void setUp() {
        service = new DemoPredictionService();
    }

    @Test
    @DisplayName("High risk profile should yield high probability (>= 0.70)")
    void testHighRiskProfile() {
        PredictionFormDto form = PredictionFormDto.builder()
            .gender(Customer.Gender.MALE)
            .seniorCitizen(true)
            .partner(false)
            .dependents(false)
            .tenure(2)
            .phoneService(true)
            .multipleLines(Customer.ServiceStatus.YES)
            .internetService(Customer.InternetService.FIBER_OPTIC)
            .onlineSecurity(Customer.ServiceStatus.NO)
            .onlineBackup(Customer.ServiceStatus.NO)
            .deviceProtection(Customer.ServiceStatus.NO)
            .techSupport(Customer.ServiceStatus.NO)
            .streamingTv(Customer.ServiceStatus.YES)
            .streamingMovies(Customer.ServiceStatus.YES)
            .contract(Customer.Contract.MONTH_TO_MONTH)
            .paperlessBilling(true)
            .paymentMethod(Customer.PaymentMethod.ELECTRONIC_CHECK)
            .monthlyCharges(new BigDecimal("105.00"))
            .totalCharges(new BigDecimal("210.00"))
            .build();

        double prob = service.computeChurnProbability(form);
        Prediction.RiskCategory category = service.categorize(prob);

        assertTrue(prob >= 0.70, "Probability should be >= 0.70 for high risk profile, got " + prob);
        assertEquals(Prediction.RiskCategory.HIGH, category);
    }

    @Test
    @DisplayName("Low risk profile should yield low probability (< 0.40)")
    void testLowRiskProfile() {
        PredictionFormDto form = PredictionFormDto.builder()
            .gender(Customer.Gender.FEMALE)
            .seniorCitizen(false)
            .partner(true)
            .dependents(true)
            .tenure(70)
            .phoneService(true)
            .multipleLines(Customer.ServiceStatus.NO)
            .internetService(Customer.InternetService.DSL)
            .onlineSecurity(Customer.ServiceStatus.YES)
            .onlineBackup(Customer.ServiceStatus.YES)
            .deviceProtection(Customer.ServiceStatus.YES)
            .techSupport(Customer.ServiceStatus.YES)
            .streamingTv(Customer.ServiceStatus.NO)
            .streamingMovies(Customer.ServiceStatus.NO)
            .contract(Customer.Contract.TWO_YEAR)
            .paperlessBilling(false)
            .paymentMethod(Customer.PaymentMethod.BANK_TRANSFER)
            .monthlyCharges(new BigDecimal("45.00"))
            .totalCharges(new BigDecimal("3150.00"))
            .build();

        double prob = service.computeChurnProbability(form);
        Prediction.RiskCategory category = service.categorize(prob);

        assertTrue(prob < 0.40, "Probability should be < 0.40 for low risk profile, got " + prob);
        assertEquals(Prediction.RiskCategory.LOW, category);
    }

    @Test
    @DisplayName("Probability should always be clamped between 0.0 and 1.0")
    void testBoundaryClamping() {
        PredictionFormDto emptyForm = new PredictionFormDto();
        double prob = service.computeChurnProbability(emptyForm);
        assertTrue(prob >= 0.0 && prob <= 1.0);
    }
}
