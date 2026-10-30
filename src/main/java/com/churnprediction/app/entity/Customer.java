package com.churnprediction.app.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Customer entity representing a telecommunications customer.
 * Stores all relevant customer attributes used for churn prediction.
 */
@Entity
@Table(name = "customers", indexes = {
    @Index(name = "idx_customer_status", columnList = "churn_status"),
    @Index(name = "idx_customer_contract", columnList = "contract")
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", unique = true, nullable = false, length = 20)
    @NotBlank(message = "Customer ID is required")
    @Size(max = 20, message = "Customer ID must not exceed 20 characters")
    private String customerId;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @Column(length = 100)
    @Email(message = "Please provide a valid email address")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Gender is required")
    private Gender gender;

    @Column(name = "senior_citizen", nullable = false)
    @NotNull(message = "Senior citizen status is required")
    private Boolean seniorCitizen;

    @Column(nullable = false)
    @NotNull(message = "Partner status is required")
    private Boolean partner;

    @Column(nullable = false)
    @NotNull(message = "Dependents status is required")
    private Boolean dependents;

    @Column(nullable = false)
    @NotNull(message = "Tenure is required")
    @Min(value = 0, message = "Tenure must be non-negative")
    @Max(value = 120, message = "Tenure cannot exceed 120 months")
    private Integer tenure;

    @Column(name = "phone_service", nullable = false)
    @NotNull(message = "Phone service status is required")
    private Boolean phoneService;

    @Enumerated(EnumType.STRING)
    @Column(name = "multiple_lines", nullable = false)
    @NotNull(message = "Multiple lines status is required")
    private ServiceStatus multipleLines;

    @Enumerated(EnumType.STRING)
    @Column(name = "internet_service", nullable = false)
    @NotNull(message = "Internet service is required")
    private InternetService internetService;

    @Enumerated(EnumType.STRING)
    @Column(name = "online_security", nullable = false)
    @NotNull(message = "Online security status is required")
    private ServiceStatus onlineSecurity;

    @Enumerated(EnumType.STRING)
    @Column(name = "online_backup", nullable = false)
    @NotNull(message = "Online backup status is required")
    private ServiceStatus onlineBackup;

    @Enumerated(EnumType.STRING)
    @Column(name = "device_protection", nullable = false)
    @NotNull(message = "Device protection status is required")
    private ServiceStatus deviceProtection;

    @Enumerated(EnumType.STRING)
    @Column(name = "tech_support", nullable = false)
    @NotNull(message = "Tech support status is required")
    private ServiceStatus techSupport;

    @Enumerated(EnumType.STRING)
    @Column(name = "streaming_tv", nullable = false)
    @NotNull(message = "Streaming TV status is required")
    private ServiceStatus streamingTv;

    @Enumerated(EnumType.STRING)
    @Column(name = "streaming_movies", nullable = false)
    @NotNull(message = "Streaming movies status is required")
    private ServiceStatus streamingMovies;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Contract type is required")
    private Contract contract;

    @Column(name = "paperless_billing", nullable = false)
    @NotNull(message = "Paperless billing status is required")
    private Boolean paperlessBilling;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @Column(name = "monthly_charges", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Monthly charges are required")
    @DecimalMin(value = "0.0", message = "Monthly charges must be non-negative")
    private BigDecimal monthlyCharges;

    @Column(name = "total_charges", nullable = false, precision = 12, scale = 2)
    @NotNull(message = "Total charges are required")
    @DecimalMin(value = "0.0", message = "Total charges must be non-negative")
    private BigDecimal totalCharges;

    @Column(name = "churn_status", nullable = false)
    @NotNull(message = "Churn status is required")
    private Boolean churnStatus = false;

    @Column(name = "is_demo_data", nullable = false)
    private Boolean isDemoData = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Prediction> predictions = new ArrayList<>();

    public Customer() {
    }

    public Customer(Long id, String customerId, String firstName, String lastName, String email,
                    Gender gender, Boolean seniorCitizen, Boolean partner, Boolean dependents,
                    Integer tenure, Boolean phoneService, ServiceStatus multipleLines,
                    InternetService internetService, ServiceStatus onlineSecurity,
                    ServiceStatus onlineBackup, ServiceStatus deviceProtection,
                    ServiceStatus techSupport, ServiceStatus streamingTv,
                    ServiceStatus streamingMovies, Contract contract, Boolean paperlessBilling,
                    PaymentMethod paymentMethod, BigDecimal monthlyCharges, BigDecimal totalCharges,
                    Boolean churnStatus, Boolean isDemoData, LocalDateTime createdAt,
                    LocalDateTime updatedAt, List<Prediction> predictions) {
        this.id = id;
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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
        this.churnStatus = churnStatus != null ? churnStatus : false;
        this.isDemoData = isDemoData != null ? isDemoData : false;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.predictions = predictions != null ? predictions : new ArrayList<>();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

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

    public ServiceStatus getMultipleLines() { return multipleLines; }
    public void setMultipleLines(ServiceStatus multipleLines) { this.multipleLines = multipleLines; }

    public InternetService getInternetService() { return internetService; }
    public void setInternetService(InternetService internetService) { this.internetService = internetService; }

    public ServiceStatus getOnlineSecurity() { return onlineSecurity; }
    public void setOnlineSecurity(ServiceStatus onlineSecurity) { this.onlineSecurity = onlineSecurity; }

    public ServiceStatus getOnlineBackup() { return onlineBackup; }
    public void setOnlineBackup(ServiceStatus onlineBackup) { this.onlineBackup = onlineBackup; }

    public ServiceStatus getDeviceProtection() { return deviceProtection; }
    public void setDeviceProtection(ServiceStatus deviceProtection) { this.deviceProtection = deviceProtection; }

    public ServiceStatus getTechSupport() { return techSupport; }
    public void setTechSupport(ServiceStatus techSupport) { this.techSupport = techSupport; }

    public ServiceStatus getStreamingTv() { return streamingTv; }
    public void setStreamingTv(ServiceStatus streamingTv) { this.streamingTv = streamingTv; }

    public ServiceStatus getStreamingMovies() { return streamingMovies; }
    public void setStreamingMovies(ServiceStatus streamingMovies) { this.streamingMovies = streamingMovies; }

    public Contract getContract() { return contract; }
    public void setContract(Contract contract) { this.contract = contract; }

    public Boolean getPaperlessBilling() { return paperlessBilling; }
    public void setPaperlessBilling(Boolean paperlessBilling) { this.paperlessBilling = paperlessBilling; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public BigDecimal getMonthlyCharges() { return monthlyCharges; }
    public void setMonthlyCharges(BigDecimal monthlyCharges) { this.monthlyCharges = monthlyCharges; }

    public BigDecimal getTotalCharges() { return totalCharges; }
    public void setTotalCharges(BigDecimal totalCharges) { this.totalCharges = totalCharges; }

    public Boolean getChurnStatus() { return churnStatus; }
    public void setChurnStatus(Boolean churnStatus) { this.churnStatus = churnStatus; }

    public Boolean getIsDemoData() { return isDemoData; }
    public void setIsDemoData(Boolean isDemoData) { this.isDemoData = isDemoData; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<Prediction> getPredictions() { return predictions; }
    public void setPredictions(List<Prediction> predictions) { this.predictions = predictions; }

    // Helpers
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isHighRisk() {
        return predictions.stream()
            .filter(p -> p.getCreatedAt() != null)
            .max((a, b) -> a.getCreatedAt().compareTo(b.getCreatedAt()))
            .map(p -> p.getRiskCategory() == Prediction.RiskCategory.HIGH)
            .orElse(false);
    }

    // Builder
    public static CustomerBuilder builder() {
        return new CustomerBuilder();
    }

    public static class CustomerBuilder {
        private Long id;
        private String customerId;
        private String firstName;
        private String lastName;
        private String email;
        private Gender gender;
        private Boolean seniorCitizen;
        private Boolean partner;
        private Boolean dependents;
        private Integer tenure;
        private Boolean phoneService;
        private ServiceStatus multipleLines;
        private InternetService internetService;
        private ServiceStatus onlineSecurity;
        private ServiceStatus onlineBackup;
        private ServiceStatus deviceProtection;
        private ServiceStatus techSupport;
        private ServiceStatus streamingTv;
        private ServiceStatus streamingMovies;
        private Contract contract;
        private Boolean paperlessBilling;
        private PaymentMethod paymentMethod;
        private BigDecimal monthlyCharges;
        private BigDecimal totalCharges;
        private Boolean churnStatus = false;
        private Boolean isDemoData = false;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private List<Prediction> predictions = new ArrayList<>();

        public CustomerBuilder id(Long id) { this.id = id; return this; }
        public CustomerBuilder customerId(String customerId) { this.customerId = customerId; return this; }
        public CustomerBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public CustomerBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public CustomerBuilder email(String email) { this.email = email; return this; }
        public CustomerBuilder gender(Gender gender) { this.gender = gender; return this; }
        public CustomerBuilder seniorCitizen(Boolean seniorCitizen) { this.seniorCitizen = seniorCitizen; return this; }
        public CustomerBuilder partner(Boolean partner) { this.partner = partner; return this; }
        public CustomerBuilder dependents(Boolean dependents) { this.dependents = dependents; return this; }
        public CustomerBuilder tenure(Integer tenure) { this.tenure = tenure; return this; }
        public CustomerBuilder phoneService(Boolean phoneService) { this.phoneService = phoneService; return this; }
        public CustomerBuilder multipleLines(ServiceStatus multipleLines) { this.multipleLines = multipleLines; return this; }
        public CustomerBuilder internetService(InternetService internetService) { this.internetService = internetService; return this; }
        public CustomerBuilder onlineSecurity(ServiceStatus onlineSecurity) { this.onlineSecurity = onlineSecurity; return this; }
        public CustomerBuilder onlineBackup(ServiceStatus onlineBackup) { this.onlineBackup = onlineBackup; return this; }
        public CustomerBuilder deviceProtection(ServiceStatus deviceProtection) { this.deviceProtection = deviceProtection; return this; }
        public CustomerBuilder techSupport(ServiceStatus techSupport) { this.techSupport = techSupport; return this; }
        public CustomerBuilder streamingTv(ServiceStatus streamingTv) { this.streamingTv = streamingTv; return this; }
        public CustomerBuilder streamingMovies(ServiceStatus streamingMovies) { this.streamingMovies = streamingMovies; return this; }
        public CustomerBuilder contract(Contract contract) { this.contract = contract; return this; }
        public CustomerBuilder paperlessBilling(Boolean paperlessBilling) { this.paperlessBilling = paperlessBilling; return this; }
        public CustomerBuilder paymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public CustomerBuilder monthlyCharges(BigDecimal monthlyCharges) { this.monthlyCharges = monthlyCharges; return this; }
        public CustomerBuilder totalCharges(BigDecimal totalCharges) { this.totalCharges = totalCharges; return this; }
        public CustomerBuilder churnStatus(Boolean churnStatus) { this.churnStatus = churnStatus; return this; }
        public CustomerBuilder isDemoData(Boolean isDemoData) { this.isDemoData = isDemoData; return this; }
        public CustomerBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public CustomerBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }
        public CustomerBuilder predictions(List<Prediction> predictions) { this.predictions = predictions; return this; }

        public Customer build() {
            return new Customer(id, customerId, firstName, lastName, email, gender, seniorCitizen,
                partner, dependents, tenure, phoneService, multipleLines, internetService,
                onlineSecurity, onlineBackup, deviceProtection, techSupport, streamingTv,
                streamingMovies, contract, paperlessBilling, paymentMethod, monthlyCharges,
                totalCharges, churnStatus, isDemoData, createdAt, updatedAt, predictions);
        }
    }

    // Enumerations
    public enum Gender { MALE, FEMALE, OTHER }

    public enum ServiceStatus { YES, NO, NO_PHONE_SERVICE, NO_INTERNET_SERVICE }

    public enum InternetService { DSL, FIBER_OPTIC, NO }

    public enum Contract { MONTH_TO_MONTH, ONE_YEAR, TWO_YEAR }

    public enum PaymentMethod {
        ELECTRONIC_CHECK, MAILED_CHECK, BANK_TRANSFER, CREDIT_CARD;

        public String getDisplayName() {
            return switch (this) {
                case ELECTRONIC_CHECK -> "Electronic Check";
                case MAILED_CHECK -> "Mailed Check";
                case BANK_TRANSFER -> "Bank Transfer (Auto)";
                case CREDIT_CARD -> "Credit Card (Auto)";
            };
        }
    }
}
