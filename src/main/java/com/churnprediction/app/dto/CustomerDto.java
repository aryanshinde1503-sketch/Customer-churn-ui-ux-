package com.churnprediction.app.dto;

import java.math.BigDecimal;

import com.churnprediction.app.entity.Customer;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for Customer form submission and API responses.
 */
public class CustomerDto {

    private Long id;

    @NotBlank(message = "Customer ID is required")
    @Size(max = 20, message = "Customer ID must not exceed 20 characters")
    private String customerId;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @Email(message = "Please provide a valid email address")
    private String email;

    @NotNull(message = "Gender is required")
    private Customer.Gender gender;

    @NotNull(message = "Senior citizen status is required")
    private Boolean seniorCitizen;

    @NotNull(message = "Partner status is required")
    private Boolean partner;

    @NotNull(message = "Dependents status is required")
    private Boolean dependents;

    @NotNull(message = "Tenure is required")
    @Min(value = 0, message = "Tenure must be non-negative")
    @Max(value = 120, message = "Tenure cannot exceed 120 months")
    private Integer tenure;

    @NotNull(message = "Phone service status is required")
    private Boolean phoneService;

    @NotNull(message = "Multiple lines status is required")
    private Customer.ServiceStatus multipleLines;

    @NotNull(message = "Internet service is required")
    private Customer.InternetService internetService;

    @NotNull(message = "Online security status is required")
    private Customer.ServiceStatus onlineSecurity;

    @NotNull(message = "Online backup status is required")
    private Customer.ServiceStatus onlineBackup;

    @NotNull(message = "Device protection status is required")
    private Customer.ServiceStatus deviceProtection;

    @NotNull(message = "Tech support status is required")
    private Customer.ServiceStatus techSupport;

    @NotNull(message = "Streaming TV status is required")
    private Customer.ServiceStatus streamingTv;

    @NotNull(message = "Streaming movies status is required")
    private Customer.ServiceStatus streamingMovies;

    @NotNull(message = "Contract type is required")
    private Customer.Contract contract;

    @NotNull(message = "Paperless billing status is required")
    private Boolean paperlessBilling;

    @NotNull(message = "Payment method is required")
    private Customer.PaymentMethod paymentMethod;

    @NotNull(message = "Monthly charges are required")
    @DecimalMin(value = "0.0", message = "Monthly charges must be non-negative")
    private BigDecimal monthlyCharges;

    @NotNull(message = "Total charges are required")
    @DecimalMin(value = "0.0", message = "Total charges must be non-negative")
    private BigDecimal totalCharges;

    @NotNull(message = "Churn status is required")
    private Boolean churnStatus;

    public CustomerDto() {
    }

    public CustomerDto(Long id, String customerId, String firstName, String lastName, String email,
                       Customer.Gender gender, Boolean seniorCitizen, Boolean partner, Boolean dependents,
                       Integer tenure, Boolean phoneService, Customer.ServiceStatus multipleLines,
                       Customer.InternetService internetService, Customer.ServiceStatus onlineSecurity,
                       Customer.ServiceStatus onlineBackup, Customer.ServiceStatus deviceProtection,
                       Customer.ServiceStatus techSupport, Customer.ServiceStatus streamingTv,
                       Customer.ServiceStatus streamingMovies, Customer.Contract contract,
                       Boolean paperlessBilling, Customer.PaymentMethod paymentMethod,
                       BigDecimal monthlyCharges, BigDecimal totalCharges, Boolean churnStatus) {
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
        this.churnStatus = churnStatus;
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

    public Boolean getChurnStatus() { return churnStatus; }
    public void setChurnStatus(Boolean churnStatus) { this.churnStatus = churnStatus; }

    // Conversion from entity
    public static CustomerDto fromEntity(Customer customer) {
        return CustomerDto.builder()
            .id(customer.getId())
            .customerId(customer.getCustomerId())
            .firstName(customer.getFirstName())
            .lastName(customer.getLastName())
            .email(customer.getEmail())
            .gender(customer.getGender())
            .seniorCitizen(customer.getSeniorCitizen())
            .partner(customer.getPartner())
            .dependents(customer.getDependents())
            .tenure(customer.getTenure())
            .phoneService(customer.getPhoneService())
            .multipleLines(customer.getMultipleLines())
            .internetService(customer.getInternetService())
            .onlineSecurity(customer.getOnlineSecurity())
            .onlineBackup(customer.getOnlineBackup())
            .deviceProtection(customer.getDeviceProtection())
            .techSupport(customer.getTechSupport())
            .streamingTv(customer.getStreamingTv())
            .streamingMovies(customer.getStreamingMovies())
            .contract(customer.getContract())
            .paperlessBilling(customer.getPaperlessBilling())
            .paymentMethod(customer.getPaymentMethod())
            .monthlyCharges(customer.getMonthlyCharges())
            .totalCharges(customer.getTotalCharges())
            .churnStatus(customer.getChurnStatus())
            .build();
    }

    // Apply DTO values to entity
    public Customer toEntity() {
        Customer customer = new Customer();
        applyToEntity(customer);
        return customer;
    }

    public void applyToEntity(Customer customer) {
        customer.setCustomerId(this.customerId);
        customer.setFirstName(this.firstName);
        customer.setLastName(this.lastName);
        customer.setEmail(this.email);
        customer.setGender(this.gender);
        customer.setSeniorCitizen(this.seniorCitizen);
        customer.setPartner(this.partner);
        customer.setDependents(this.dependents);
        customer.setTenure(this.tenure);
        customer.setPhoneService(this.phoneService);
        customer.setMultipleLines(this.multipleLines);
        customer.setInternetService(this.internetService);
        customer.setOnlineSecurity(this.onlineSecurity);
        customer.setOnlineBackup(this.onlineBackup);
        customer.setDeviceProtection(this.deviceProtection);
        customer.setTechSupport(this.techSupport);
        customer.setStreamingTv(this.streamingTv);
        customer.setStreamingMovies(this.streamingMovies);
        customer.setContract(this.contract);
        customer.setPaperlessBilling(this.paperlessBilling);
        customer.setPaymentMethod(this.paymentMethod);
        customer.setMonthlyCharges(this.monthlyCharges);
        customer.setTotalCharges(this.totalCharges);
        customer.setChurnStatus(this.churnStatus);
    }

    // Builder
    public static CustomerDtoBuilder builder() {
        return new CustomerDtoBuilder();
    }

    public static class CustomerDtoBuilder {
        private Long id;
        private String customerId;
        private String firstName;
        private String lastName;
        private String email;
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
        private Boolean churnStatus;

        public CustomerDtoBuilder id(Long id) { this.id = id; return this; }
        public CustomerDtoBuilder customerId(String customerId) { this.customerId = customerId; return this; }
        public CustomerDtoBuilder firstName(String firstName) { this.firstName = firstName; return this; }
        public CustomerDtoBuilder lastName(String lastName) { this.lastName = lastName; return this; }
        public CustomerDtoBuilder email(String email) { this.email = email; return this; }
        public CustomerDtoBuilder gender(Customer.Gender gender) { this.gender = gender; return this; }
        public CustomerDtoBuilder seniorCitizen(Boolean seniorCitizen) { this.seniorCitizen = seniorCitizen; return this; }
        public CustomerDtoBuilder partner(Boolean partner) { this.partner = partner; return this; }
        public CustomerDtoBuilder dependents(Boolean dependents) { this.dependents = dependents; return this; }
        public CustomerDtoBuilder tenure(Integer tenure) { this.tenure = tenure; return this; }
        public CustomerDtoBuilder phoneService(Boolean phoneService) { this.phoneService = phoneService; return this; }
        public CustomerDtoBuilder multipleLines(Customer.ServiceStatus multipleLines) { this.multipleLines = multipleLines; return this; }
        public CustomerDtoBuilder internetService(Customer.InternetService internetService) { this.internetService = internetService; return this; }
        public CustomerDtoBuilder onlineSecurity(Customer.ServiceStatus onlineSecurity) { this.onlineSecurity = onlineSecurity; return this; }
        public CustomerDtoBuilder onlineBackup(Customer.ServiceStatus onlineBackup) { this.onlineBackup = onlineBackup; return this; }
        public CustomerDtoBuilder deviceProtection(Customer.ServiceStatus deviceProtection) { this.deviceProtection = deviceProtection; return this; }
        public CustomerDtoBuilder techSupport(Customer.ServiceStatus techSupport) { this.techSupport = techSupport; return this; }
        public CustomerDtoBuilder streamingTv(Customer.ServiceStatus streamingTv) { this.streamingTv = streamingTv; return this; }
        public CustomerDtoBuilder streamingMovies(Customer.ServiceStatus streamingMovies) { this.streamingMovies = streamingMovies; return this; }
        public CustomerDtoBuilder contract(Customer.Contract contract) { this.contract = contract; return this; }
        public CustomerDtoBuilder paperlessBilling(Boolean paperlessBilling) { this.paperlessBilling = paperlessBilling; return this; }
        public CustomerDtoBuilder paymentMethod(Customer.PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; return this; }
        public CustomerDtoBuilder monthlyCharges(BigDecimal monthlyCharges) { this.monthlyCharges = monthlyCharges; return this; }
        public CustomerDtoBuilder totalCharges(BigDecimal totalCharges) { this.totalCharges = totalCharges; return this; }
        public CustomerDtoBuilder churnStatus(Boolean churnStatus) { this.churnStatus = churnStatus; return this; }

        public CustomerDto build() {
            return new CustomerDto(id, customerId, firstName, lastName, email, gender, seniorCitizen,
                partner, dependents, tenure, phoneService, multipleLines, internetService,
                onlineSecurity, onlineBackup, deviceProtection, techSupport, streamingTv,
                streamingMovies, contract, paperlessBilling, paymentMethod, monthlyCharges,
                totalCharges, churnStatus);
        }
    }
}
