package com.churnprediction.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.churnprediction.app.dto.CustomerDto;
import com.churnprediction.app.entity.Customer;

/**
 * Service interface for Customer operations.
 */
public interface CustomerService {

    Customer createCustomer(CustomerDto dto);

    Customer updateCustomer(Long id, CustomerDto dto);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByCustomerId(String customerId);

    void deleteCustomer(Long id);

    Page<Customer> searchAndFilter(String search, Boolean churnStatus,
                                   Customer.Contract contract,
                                   Customer.InternetService internetService,
                                   Pageable pageable);

    List<Customer> findAll();

    List<Customer> findRecentCustomers();

    List<Customer> findHighRiskCustomers();

    long countTotal();

    long countByChurnStatus(Boolean churnStatus);

    boolean existsByCustomerId(String customerId);
}
