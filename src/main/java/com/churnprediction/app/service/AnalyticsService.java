package com.churnprediction.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.churnprediction.app.entity.Customer;
import com.churnprediction.app.repository.CustomerRepository;

/**
 * Service for analytics chart data aggregation.
 */
@Service
@Transactional(readOnly = true)
public class AnalyticsService {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsService.class);

    private final CustomerRepository customerRepository;

    public AnalyticsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Map<String, Object> getChurnDistribution() {
        long churned = customerRepository.countByChurnStatus(true);
        long active = customerRepository.countByChurnStatus(false);
        return Map.of(
            "labels", List.of("Churned", "Active"),
            "data", List.of(churned, active),
            "colors", List.of("#ef4444", "#22c55e")
        );
    }

    public Map<String, Object> getChurnByContract() {
        List<Object[]> raw = customerRepository.churnByContract();
        Map<Customer.Contract, long[]> map = new LinkedHashMap<>();
        for (Customer.Contract c : Customer.Contract.values()) map.put(c, new long[]{0, 0});
        for (Object[] row : raw) {
            Customer.Contract contract = (Customer.Contract) row[0];
            boolean churned = (Boolean) row[1];
            long count = (Long) row[2];
            map.get(contract)[churned ? 0 : 1] = count;
        }
        List<String> labels = new ArrayList<>();
        List<Long> churnedData = new ArrayList<>();
        List<Long> activeData = new ArrayList<>();
        map.forEach((contract, counts) -> {
            labels.add(formatEnum(contract.name()));
            churnedData.add(counts[0]);
            activeData.add(counts[1]);
        });
        return Map.of("labels", labels, "churned", churnedData, "active", activeData);
    }

    public Map<String, Object> getChurnByInternetService() {
        List<Object[]> raw = customerRepository.churnByInternetService();
        Map<Customer.InternetService, long[]> map = new LinkedHashMap<>();
        for (Customer.InternetService s : Customer.InternetService.values()) map.put(s, new long[]{0, 0});
        for (Object[] row : raw) {
            Customer.InternetService svc = (Customer.InternetService) row[0];
            boolean churned = (Boolean) row[1];
            long count = (Long) row[2];
            map.get(svc)[churned ? 0 : 1] = count;
        }
        List<String> labels = new ArrayList<>();
        List<Long> churnedData = new ArrayList<>();
        List<Long> activeData = new ArrayList<>();
        map.forEach((svc, counts) -> {
            labels.add(formatEnum(svc.name()));
            churnedData.add(counts[0]);
            activeData.add(counts[1]);
        });
        return Map.of("labels", labels, "churned", churnedData, "active", activeData);
    }

    public Map<String, Object> getChurnByPaymentMethod() {
        List<Object[]> raw = customerRepository.churnByPaymentMethod();
        Map<Customer.PaymentMethod, long[]> map = new LinkedHashMap<>();
        for (Customer.PaymentMethod pm : Customer.PaymentMethod.values()) map.put(pm, new long[]{0, 0});
        for (Object[] row : raw) {
            Customer.PaymentMethod pm = (Customer.PaymentMethod) row[0];
            boolean churned = (Boolean) row[1];
            long count = (Long) row[2];
            map.get(pm)[churned ? 0 : 1] = count;
        }
        List<String> labels = new ArrayList<>();
        List<Long> churnedData = new ArrayList<>();
        List<Long> activeData = new ArrayList<>();
        map.forEach((pm, counts) -> {
            labels.add(pm.getDisplayName());
            churnedData.add(counts[0]);
            activeData.add(counts[1]);
        });
        return Map.of("labels", labels, "churned", churnedData, "active", activeData);
    }

    public Map<String, Object> getChurnByTenure() {
        List<Object[]> raw = customerRepository.churnByTenureBucket();
        Map<String, long[]> map = new LinkedHashMap<>();
        String[] buckets = {"0-12 months", "13-24 months", "25-36 months", "37-48 months", "49-60 months", "60+ months"};
        for (String b : buckets) map.put(b, new long[]{0, 0});
        for (Object[] row : raw) {
            String bucket = (String) row[0];
            boolean churned = (Boolean) row[1];
            long count = (Long) row[2];
            if (map.containsKey(bucket)) map.get(bucket)[churned ? 0 : 1] = count;
        }
        List<String> labels = new ArrayList<>();
        List<Long> churnedData = new ArrayList<>();
        List<Long> activeData = new ArrayList<>();
        map.forEach((bucket, counts) -> {
            labels.add(bucket);
            churnedData.add(counts[0]);
            activeData.add(counts[1]);
        });
        return Map.of("labels", labels, "churned", churnedData, "active", activeData);
    }

    public Map<String, Object> getChurnByMonthlyCharges() {
        List<Object[]> raw = customerRepository.churnByMonthlyChargesBucket();
        Map<String, long[]> map = new LinkedHashMap<>();
        String[] buckets = {"Under $30", "$30-$59", "$60-$89", "$90+"};
        for (String b : buckets) map.put(b, new long[]{0, 0});
        for (Object[] row : raw) {
            String bucket = (String) row[0];
            boolean churned = (Boolean) row[1];
            long count = (Long) row[2];
            if (map.containsKey(bucket)) map.get(bucket)[churned ? 0 : 1] = count;
        }
        List<String> labels = new ArrayList<>();
        List<Long> churnedData = new ArrayList<>();
        List<Long> activeData = new ArrayList<>();
        map.forEach((bucket, counts) -> {
            labels.add(bucket);
            churnedData.add(counts[0]);
            activeData.add(counts[1]);
        });
        return Map.of("labels", labels, "churned", churnedData, "active", activeData);
    }

    public Map<String, Object> getContractDistribution() {
        List<Object[]> raw = customerRepository.countByContractGrouped();
        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();
        for (Object[] row : raw) {
            labels.add(formatEnum(((Customer.Contract) row[0]).name()));
            data.add((Long) row[1]);
        }
        return Map.of("labels", labels, "data", data);
    }

    public Map<String, Object> getGenderDistribution() {
        List<Object[]> raw = customerRepository.countByGenderGrouped();
        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();
        for (Object[] row : raw) {
            labels.add(formatEnum(((Customer.Gender) row[0]).name()));
            data.add((Long) row[1]);
        }
        return Map.of("labels", labels, "data", data);
    }

    private String formatEnum(String name) {
        return Arrays.stream(name.split("_"))
            .map(w -> w.charAt(0) + w.substring(1).toLowerCase())
            .reduce((a, b) -> a + " " + b)
            .orElse(name);
    }
}
