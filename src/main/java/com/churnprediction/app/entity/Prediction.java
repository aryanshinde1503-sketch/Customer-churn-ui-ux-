package com.churnprediction.app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

/**
 * Prediction entity storing churn prediction results for customers.
 * One customer can have multiple predictions over time.
 */
@Entity
@Table(name = "predictions", indexes = {
    @Index(name = "idx_prediction_customer", columnList = "customer_id"),
    @Index(name = "idx_prediction_category", columnList = "risk_category"),
    @Index(name = "idx_prediction_created", columnList = "created_at")
})
public class Prediction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Customer is required")
    private Customer customer;

    @Column(name = "churn_probability", nullable = false, precision = 5, scale = 4)
    @NotNull(message = "Churn probability is required")
    @DecimalMin(value = "0.0", message = "Probability must be between 0 and 1")
    @DecimalMax(value = "1.0", message = "Probability must be between 0 and 1")
    private BigDecimal churnProbability;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_category", nullable = false)
    @NotNull(message = "Risk category is required")
    private RiskCategory riskCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "prediction_method", nullable = false)
    private PredictionMethod predictionMethod = PredictionMethod.DEMO;

    @Column(name = "notes", length = 500)
    private String notes;

    @Column(name = "input_snapshot", columnDefinition = "TEXT")
    private String inputSnapshot;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Risk thresholds (configurable via application.properties)
    public static final double LOW_THRESHOLD = 0.40;
    public static final double HIGH_THRESHOLD = 0.70;

    public Prediction() {
    }

    public Prediction(Long id, Customer customer, BigDecimal churnProbability,
                      RiskCategory riskCategory, PredictionMethod predictionMethod,
                      String notes, String inputSnapshot, LocalDateTime createdAt) {
        this.id = id;
        this.customer = customer;
        this.churnProbability = churnProbability;
        this.riskCategory = riskCategory;
        this.predictionMethod = predictionMethod != null ? predictionMethod : PredictionMethod.DEMO;
        this.notes = notes;
        this.inputSnapshot = inputSnapshot;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public BigDecimal getChurnProbability() { return churnProbability; }
    public void setChurnProbability(BigDecimal churnProbability) { this.churnProbability = churnProbability; }

    public RiskCategory getRiskCategory() { return riskCategory; }
    public void setRiskCategory(RiskCategory riskCategory) { this.riskCategory = riskCategory; }

    public PredictionMethod getPredictionMethod() { return predictionMethod; }
    public void setPredictionMethod(PredictionMethod predictionMethod) { this.predictionMethod = predictionMethod; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getInputSnapshot() { return inputSnapshot; }
    public void setInputSnapshot(String inputSnapshot) { this.inputSnapshot = inputSnapshot; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    /**
     * Determines risk category from probability using configurable thresholds.
     */
    public static RiskCategory categorize(double probability) {
        if (probability >= HIGH_THRESHOLD) return RiskCategory.HIGH;
        if (probability >= LOW_THRESHOLD) return RiskCategory.MEDIUM;
        return RiskCategory.LOW;
    }

    public int getProbabilityPercent() {
        return churnProbability != null
            ? (int) (churnProbability.doubleValue() * 100)
            : 0;
    }

    // Builder
    public static PredictionBuilder builder() {
        return new PredictionBuilder();
    }

    public static class PredictionBuilder {
        private Long id;
        private Customer customer;
        private BigDecimal churnProbability;
        private RiskCategory riskCategory;
        private PredictionMethod predictionMethod = PredictionMethod.DEMO;
        private String notes;
        private String inputSnapshot;
        private LocalDateTime createdAt;

        public PredictionBuilder id(Long id) { this.id = id; return this; }
        public PredictionBuilder customer(Customer customer) { this.customer = customer; return this; }
        public PredictionBuilder churnProbability(BigDecimal churnProbability) { this.churnProbability = churnProbability; return this; }
        public PredictionBuilder riskCategory(RiskCategory riskCategory) { this.riskCategory = riskCategory; return this; }
        public PredictionBuilder predictionMethod(PredictionMethod predictionMethod) { this.predictionMethod = predictionMethod; return this; }
        public PredictionBuilder notes(String notes) { this.notes = notes; return this; }
        public PredictionBuilder inputSnapshot(String inputSnapshot) { this.inputSnapshot = inputSnapshot; return this; }
        public PredictionBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public Prediction build() {
            return new Prediction(id, customer, churnProbability, riskCategory, predictionMethod, notes, inputSnapshot, createdAt);
        }
    }

    public enum RiskCategory {
        LOW, MEDIUM, HIGH;

        public String getBadgeClass() {
            return switch (this) {
                case LOW -> "badge-success";
                case MEDIUM -> "badge-warning";
                case HIGH -> "badge-danger";
            };
        }

        public String getColorClass() {
            return switch (this) {
                case LOW -> "text-success";
                case MEDIUM -> "text-warning";
                case HIGH -> "text-danger";
            };
        }
    }

    public enum PredictionMethod {
        DEMO("Demo Rule-Based Engine"),
        ML_MODEL("XGBoost ML Model");

        private final String label;

        PredictionMethod(String label) { this.label = label; }

        public String getLabel() { return label; }
    }
}
