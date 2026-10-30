package com.churnprediction.app.dto;

import java.math.BigDecimal;

import com.churnprediction.app.entity.Customer;

/**
 * DTO for churn prediction form input.
 * Decoupled from Customer entity to allow independent validation for prediction form.
 */
public class PredictionFormDto {

    private Long existingCustomerId;
    private Customer.Gender gender;
    private Boolean seniorCitizen;
    private Boolean partner;
    private Boolean dependents;
    private Integer tenure;
    private Boolean phoneService;
    private Customer.ServiceStatus multipleLines;
    private Customer.InternetService internetService;
    private Customer.ServiceStatus onlineSecurity;
    private Customer.ServiceStatus onlineBackup;
    private Customer.ServiceStatus deviceProtection;
    private Customer.ServiceStatus techSupport;
    private Customer.ServiceStatus streamingTv;
    private Customer.ServiceStatus streamingMovies;
    private Customer.Contract contract;
    private Boolean paperlessBilling;
    private Customer.PaymentMethod paymentMethod;
    private BigDecimal monthlyCharges;
    private BigDecimal totalCharges;
    private String notes;

    public PredictionFormDto() {
    }

    public PredictionFormDto(Long existingCustomerId, Customer.Gender gender, Boolean seniorCitizen,
                             Boolean partner, Boolean dependents, Integer tenure, Boolean phoneService,
                             Customer.ServiceStatus multipleLines, Customer.InternetService internetService,
                             Customer.ServiceStatus onlineSecurity, Customer.ServiceStatus onlineBackup,
                             Customer.ServiceStatus deviceProtection, Customer.ServiceStatus techSupport,
                             Customer.ServiceStatus streamingTv, Customer.ServiceStatus streamingMovies,
                             Customer.Contract contract, Boolean paperlessBilling,
                             Customer.PaymentMethod paymentMethod, BigDecimal monthlyCharges,
                             BigDecimal totalCharges, String notes) {
        this.existingCustomerId = existingCustomerId;
        this.gender = gender;
        this.seniorCitizen = seniorCitizen;
        this.partner = partner;
        this.dependents = dependents;
        this.tenure = tenure;
        this.phoneService = phoneService;
        this.multipleLines = multipleLines;
        this.internetService = internetService;
        this.onlineSecurity = onlineSecurity;
        this.onlineBackup = onlineBackup;
        this.deviceProtection = deviceProtection;
        this.techSupport = techSupport;
        this.streamingTv = streamingTv;
        this.streamingMovies = streamingMovies;
        this.contract = contract;
        this.paperlessBilling = paperlessBilling;
        this.paymentMethod = paymentMethod;
        this.monthlyCharges = monthlyCharges;
        this.totalCharges = totalCharges;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getExistingCustomerId() { return existingCustomerId; }
    public void setExistingCustomerId(Long existingCustomerId) { this.existingCustomerId = existingCustomerId; }

    public Customer.Gender getGender() { return gender; }
    public void setGender(Customer.Gender gender) { this.gender = gender; }

    public Boolean getSeniorCitizen() { return seniorCitizen; }
    public void setSeniorCitizen(Boolean seniorCitizen) { this.seniorCitizen = seniorCitizen; }

    public Boolean getPartner() { return partner; }
    public void setPartner(Boolean partner) { this.partner = partner; }

    public Boolean getDependents() { return dependents; }
    public void setDependents(Boolean dependents) { this.dependents = dependents; }

    public Integer getTenure() { return tenure; }
    public void setTenure(Integer tenure) { this.tenure = tenure; }

    public Boolean getPhoneService() { return phoneService; }
    public void setPhoneService(Boolean phoneService) { this.phoneService = phoneService; }

    public Customer.ServiceStatus getMultipleLines() { return multipleLines; }
    public void setMultipleLines(Customer.ServiceStatus multipleLines) { this.multipleLines = multipleLines; }

    public Customer.InternetService getInternetService() { return internetService; }
    public void setInternetService(Customer.InternetService internetService) { this.internetService = internetService; }

    public Customer.ServiceStatus getOnlineSecurity() { return onlineSecurity; }
    public void setOnlineSecurity(Customer.ServiceStatus onlineSecurity) { this.onlineSecurity = onlineSecurity; }

    public Customer.ServiceStatus getOnlineBackup() { return onlineBackup; }
    public void setOnlineBackup(Customer.ServiceStatus onlineBackup) { this.onlineBackup = onlineBackup; }

    public Customer.ServiceStatus getDeviceProtection() { return deviceProtection; }
    public void setDeviceProtection(Customer.ServiceStatus deviceProtection) { this.deviceProtection = deviceProtection; }

    public Customer.ServiceStatus getTechSupport() { return techSupport; }
    public void setTechSupport(Customer.ServiceStatus techSupport) { this.techSupport = techSupport; }

    public Customer.ServiceStatus getStreamingTv() { return streamingTv; }
    public void setStreamingTv(Customer.ServiceStatus streamingTv) { this.streamingTv = streamingTv; }

    public Customer.ServiceStatus getStreamingMovies() { return streamingMovies; }
    public void setStreamingMovies(Customer.ServiceStatus streamingMovies) { this.streamingMovies = streamingMovies; }

    public Customer.Contract getContract() { return contract; }
    public void setContract(Customer.Contract contract) { this.contract = contract; }

    public Boolean getPaperlessBilling() { return paperlessBilling; }
    public void setPaperlessBilling(Boolean paperlessBilling) { this.paperlessBilling = paperlessBilling; }

    public Customer.PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(Customer.PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public BigDecimal getMonthlyCharges() { return monthlyCharges; }
    public void setMonthlyCharges(BigDecimal monthlyCharges) { this.monthlyCharges = monthlyCharges; }

    public BigDecimal getTotalCharges() { return totalCharges; }
    public void setTotalCharges(BigDecimal totalCharges) { this.totalCharges = totalCharges; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // Builder
    public static PredictionFormDtoBuilder builder() {
        return new PredictionFormDtoBuilder();
    }

    public static class PredictionFormDtoBuilder {
        private Long existingCustomerId;
        private Customer.Gender gender;
        private Boolean seniorCitizen;
        private Boolean partner;
        private Boolean dependents;
        private Integer tenure;
        private Boolean phoneService;
        private Customer.ServiceStatus multipleLines;
        private Customer.InternetService internetService;
        private Customer.ServiceStatus onlineSecurity;
        private Customer.ServiceStatus onlineBackup;
        private Customer.ServiceStatus deviceProtection;
        private Customer.ServiceStatus techSupport;
        private Customer.ServiceStatus streamingTv;
        private Customer.ServiceStatus streamingMovies;
        private Customer.Contract contract;
        private Boolean paperlessBilling;
        private Customer.PaymentMethod paymentMethod;
        private BigDecimal monthlyCharges;
        private BigDecimal totalCharges;
        private String notes;

        public PredictionFormDtoBuilder existingCustomerId(Long existingCustomerId) { this.existingCustomerId = existingCustomerId; return this; }
        public PredictionFormDtoBuilder gender(Customer.Gender gender) { this.gender = gender; return this; }
        public PredictionFormDtoBuilder seniorCitizen(Boolean seniorCitizen) { this.seniorCitizen = seniorCitizen; return this; }
        public PredictionFormDtoBuilder partner(Boolean partner) { this.partner = partner; return this; }
        public PredictionFormDtoBuilder dependents(Boolean dependents) { this.dependents = dependents; return this; }
        public PredictionFormDtoBuilder tenure(Integer tenure) { this.tenure = tenure; return this; }
        public PredictionFormDtoBuilder phoneService(Boolean phoneService) { this.phoneService = phoneService; return this; }
        public PredictionFormDtoBuilder multipleLines(Customer.ServiceStatus multipleLines) { this.multipleLines = multipleLines; return this; }
        public PredictionFormDtoBuilder internetService(Customer.InternetService internetService) { this.internetService = internetService; return this; }
        public PredictionFormDtoBuilder onlineSecurity(Customer.ServiceStatus onlineSecurity) { this.onlineSecurity = onlineSecurity; return this; }
        public PredictionFormDtoBuilder onlineBackup(Customer.ServiceStatus onlineBackup) { this.onlineBackup = onlineBackup; return this; }
        public PredictionFormDtoBuilder deviceProtection(Customer.ServiceStatus deviceProtection) { this.deviceProtection = deviceProtection; return this; }
        public PredictionFormDtoBuilder techSupport(Customer.ServiceStatus techSupport) { this.techSupport = techSupport; return this; }
        public PredictionFormDtoBuilder streamingTv(Customer.ServiceStatus streamingTv) { this.streamingTv = streamingTv; return this; }
        public PredictionFormDtoBuilder streamingMovies(Customer.ServiceStatus streamingMovies) { this.streamingMovies = streamingMovies; return this; }
        public PredictionFormDtoBuilder contract(Customer.Contract contract) { this.contract = contract; return this; }
        public PredictionFormDtoBuilder paperlessBilling(Boolean paperlessBilling) { this.paperlessBilling = paperlessBilling; return this; }
        public PredictionFormDtoBuilder paymentMethod(Customer.PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public PredictionFormDtoBuilder monthlyCharges(BigDecimal monthlyCharges) { this.monthlyCharges = monthlyCharges; return this; }
        public PredictionFormDtoBuilder totalCharges(BigDecimal totalCharges) { this.totalCharges = totalCharges; return this; }
        public PredictionFormDtoBuilder notes(String notes) { this.notes = notes; return this; }

        public PredictionFormDto build() {
            return new PredictionFormDto(existingCustomerId, gender, seniorCitizen, partner, dependents,
                tenure, phoneService, multipleLines, internetService, onlineSecurity, onlineBackup,
                deviceProtection, techSupport, streamingTv, streamingMovies, contract, paperlessBilling,
                paymentMethod, monthlyCharges, totalCharges, notes);
        }
    }
}
