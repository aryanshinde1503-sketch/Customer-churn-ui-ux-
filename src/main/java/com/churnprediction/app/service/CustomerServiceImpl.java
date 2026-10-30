package com.churnprediction.app.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.churnprediction.app.dto.CustomerDto;
import com.churnprediction.app.entity.Customer;
import com.churnprediction.app.exception.CustomerNotFoundException;
import com.churnprediction.app.exception.DuplicateCustomerIdException;
import com.churnprediction.app.repository.CustomerRepository;

/**
 * Implementation of CustomerService with full CRUD and business logic.
 * Employs Spring constructor dependency injection and SLF4J logging.
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(CustomerDto dto) {
        log.info("Creating customer with ID: {}", dto.getCustomerId());

        if (customerRepository.existsByCustomerId(dto.getCustomerId())) {
            throw new DuplicateCustomerIdException(
                "Customer with ID '" + dto.getCustomerId() + "' already exists.");
        }

        Customer customer = dto.toEntity();
        Customer saved = customerRepository.save(customer);
        log.info("Customer created successfully with DB ID: {}", saved.getId());
        return saved;
    }

    @Override
    public Customer updateCustomer(Long id, CustomerDto dto) {
        log.info("Updating customer with DB ID: {}", id);

        Customer existing = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        // Check if customerId is being changed to one that already exists
        if (!existing.getCustomerId().equals(dto.getCustomerId()) &&
                customerRepository.existsByCustomerId(dto.getCustomerId())) {
            throw new DuplicateCustomerIdException(
                "Customer with ID '" + dto.getCustomerId() + "' already exists.");
        }

        dto.applyToEntity(existing);
        Customer updated = customerRepository.save(existing);
        log.info("Customer updated successfully: {}", updated.getCustomerId());
        return updated;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findByCustomerId(String customerId) {
        return customerRepository.findByCustomerId(customerId);
    }

    @Override
    public void deleteCustomer(Long id) {
        log.info("Deleting customer with DB ID: {}", id);
        Customer customer = customerRepository.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
        customerRepository.delete(customer);
        log.info("Customer deleted: {}", customer.getCustomerId());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Customer> searchAndFilter(String search, Boolean churnStatus,
                                          Customer.Contract contract,
                                          Customer.InternetService internetService,
                                          Pageable pageable) {
        return customerRepository.searchAndFilter(search, churnStatus, contract, internetService, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findRecentCustomers() {
        return customerRepository.findTop10ByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findHighRiskCustomers() {
        return customerRepository.findHighRiskCustomers();
    }

    @Override
    @Transactional(readOnly = true)
    public long countTotal() {
        return customerRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countByChurnStatus(Boolean churnStatus) {
        return customerRepository.countByChurnStatus(churnStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCustomerId(String customerId) {
        return customerRepository.existsByCustomerId(customerId);
    }
}
