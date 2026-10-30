package com.churnprediction.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.churnprediction.app.entity.Prediction;

/**
 * DTO for Prediction display and creation.
 */
public class PredictionDto {

    private Long id;
    private Long customerId;
    private String customerDbId;
    private String customerName;
    private BigDecimal churnProbability;
    private int probabilityPercent;
    private Prediction.RiskCategory riskCategory;
    private Prediction.PredictionMethod predictionMethod;
    private String notes;
    private LocalDateTime createdAt;

    public PredictionDto() {
    }

    public PredictionDto(Long id, Long customerId, String customerDbId, String customerName,
                         BigDecimal churnProbability, int probabilityPercent,
                         Prediction.RiskCategory riskCategory, Prediction.PredictionMethod predictionMethod,
                         String notes, LocalDateTime createdAt) {
        this.id = id;
        this.customerId = customerId;
        this.customerDbId = customerDbId;
        this.customerName = customerName;
        this.churnProbability = churnProbability;
        this.probabilityPercent = probabilityPercent;
        this.riskCategory = riskCategory;
        this.predictionMethod = predictionMethod;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getCustomerDbId() { return customerDbId; }
    public void setCustomerDbId(String customerDbId) { this.customerDbId = customerDbId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public BigDecimal getChurnProbability() { return churnProbability; }
    public void setChurnProbability(BigDecimal churnProbability) { this.churnProbability = churnProbability; }

    public int getProbabilityPercent() { return probabilityPercent; }
    public void setProbabilityPercent(int probabilityPercent) { this.probabilityPercent = probabilityPercent; }

    public Prediction.RiskCategory getRiskCategory() { return riskCategory; }
    public void setRiskCategory(Prediction.RiskCategory riskCategory) { this.riskCategory = riskCategory; }

    public Prediction.PredictionMethod getPredictionMethod() { return predictionMethod; }
    public void setPredictionMethod(Prediction.PredictionMethod predictionMethod) { this.predictionMethod = predictionMethod; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public static PredictionDto fromEntity(Prediction prediction) {
        String name = prediction.getCustomer() != null
            ? prediction.getCustomer().getFullName()
            : "Unknown";
        String custId = prediction.getCustomer() != null
            ? prediction.getCustomer().getCustomerId()
            : "";

        return PredictionDto.builder()
            .id(prediction.getId())
            .customerId(prediction.getCustomer() != null ? prediction.getCustomer().getId() : null)
            .customerDbId(custId)
            .customerName(name)
            .churnProbability(prediction.getChurnProbability())
            .probabilityPercent(prediction.getProbabilityPercent())
            .riskCategory(prediction.getRiskCategory())
            .predictionMethod(prediction.getPredictionMethod())
            .notes(prediction.getNotes())
            .createdAt(prediction.getCreatedAt())
            .build();
    }

    // Builder
    public static PredictionDtoBuilder builder() {
        return new PredictionDtoBuilder();
    }

    public static class PredictionDtoBuilder {
        private Long id;
        private Long customerId;
        private String customerDbId;
        private String customerName;
        private BigDecimal churnProbability;
        private int probabilityPercent;
        private Prediction.RiskCategory riskCategory;
        private Prediction.PredictionMethod predictionMethod;
        private String notes;
        private LocalDateTime createdAt;

        public PredictionDtoBuilder id(Long id) { this.id = id; return this; }
        public PredictionDtoBuilder customerId(Long customerId) { this.customerId = customerId; return this; }
        public PredictionDtoBuilder customerDbId(String customerDbId) { this.customerDbId = customerDbId; return this; }
        public PredictionDtoBuilder customerName(String customerName) { this.customerName = customerName; return this; }
        public PredictionDtoBuilder churnProbability(BigDecimal churnProbability) { this.churnProbability = churnProbability; return this; }
        public PredictionDtoBuilder probabilityPercent(int probabilityPercent) { this.probabilityPercent = probabilityPercent; return this; }
        public PredictionDtoBuilder riskCategory(Prediction.RiskCategory riskCategory) { this.riskCategory = riskCategory; return this; }
        public PredictionDtoBuilder predictionMethod(Prediction.PredictionMethod predictionMethod) { this.predictionMethod = predictionMethod; return this; }
        public PredictionDtoBuilder notes(String notes) { this.notes = notes; return this; }
        public PredictionDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }

        public PredictionDto build() {
            return new PredictionDto(id, customerId, customerDbId, customerName, churnProbability,
                probabilityPercent, riskCategory, predictionMethod, notes, createdAt);
        }
    }
}
