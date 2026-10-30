package com.churnprediction.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.churnprediction.app.dto.PredictionDto;
import com.churnprediction.app.dto.PredictionFormDto;
import com.churnprediction.app.entity.Customer;
import com.churnprediction.app.entity.Prediction;
import com.churnprediction.app.exception.CustomerNotFoundException;
import com.churnprediction.app.repository.CustomerRepository;
import com.churnprediction.app.repository.PredictionRepository;

/**
 * Implementation of PredictionService.
 * Orchestrates demo prediction scoring and persistence.
 */
@Service
@Transactional
public class PredictionServiceImpl implements PredictionService {

    private static final Logger log = LoggerFactory.getLogger(PredictionServiceImpl.class);

    private final PredictionRepository predictionRepository;
    private final CustomerRepository customerRepository;
    private final DemoPredictionService demoPredictionService;

    public PredictionServiceImpl(PredictionRepository predictionRepository,
                                 CustomerRepository customerRepository,
                                 DemoPredictionService demoPredictionService) {
        this.predictionRepository = predictionRepository;
        this.customerRepository = customerRepository;
        this.demoPredictionService = demoPredictionService;
    }

    @Override
    public PredictionDto predict(PredictionFormDto form, Long customerId) {
        log.info("Running demo prediction for customer ID: {}", customerId);

        double probability = demoPredictionService.computeChurnProbability(form);
        Prediction.RiskCategory category = demoPredictionService.categorize(probability);

        Customer customer = null;
        if (customerId != null) {
            customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));
        }

        return PredictionDto.builder()
            .customerId(customerId)
            .customerDbId(customer != null ? customer.getCustomerId() : null)
            .customerName(customer != null ? customer.getFullName() : "Ad-hoc Prediction")
            .churnProbability(demoPredictionService.roundedProbability(probability))
            .probabilityPercent((int) Math.round(probability * 100))
            .riskCategory(category)
            .predictionMethod(Prediction.PredictionMethod.DEMO)
            .notes(form.getNotes())
            .build();
    }

    @Override
    public Prediction savePrediction(PredictionDto dto, Long customerId) {
        log.info("Saving prediction for customer ID: {}", customerId);

        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));

        Prediction prediction = Prediction.builder()
            .customer(customer)
            .churnProbability(dto.getChurnProbability())
            .riskCategory(dto.getRiskCategory())
            .predictionMethod(Prediction.PredictionMethod.DEMO)
            .notes(dto.getNotes())
            .build();

        Prediction saved = predictionRepository.save(prediction);
        log.info("Prediction saved with ID: {}", saved.getId());
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prediction> findById(Long id) {
        return predictionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Prediction> searchAndFilter(String search, Prediction.RiskCategory riskCategory, Pageable pageable) {
        return predictionRepository.searchAndFilter(search, riskCategory, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Prediction> findByCustomerId(Long customerId) {
        return predictionRepository.findByCustomerId(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PredictionDto> findRecentPredictions(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return predictionRepository.findTop10ByOrderByCreatedAtDesc(pageable)
            .stream()
            .map(PredictionDto::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long countTotal() {
        return predictionRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countByRiskCategory(Prediction.RiskCategory category) {
        return predictionRepository.countByRiskCategory(category);
    }
}
