package com.churnprediction.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.churnprediction.app.entity.Customer;

/**
 * Repository for Customer data access operations.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerId(String customerId);

    boolean existsByCustomerId(String customerId);

    // Churn statistics
    long countByChurnStatus(Boolean churnStatus);

    long countBySeniorCitizen(Boolean seniorCitizen);

    long countByContract(Customer.Contract contract);

    long countByInternetService(Customer.InternetService internetService);

    long countByPaymentMethod(Customer.PaymentMethod paymentMethod);

    // Search and filter
    @Query("SELECT c FROM Customer c WHERE " +
           "(:search IS NULL OR :search = '' OR " +
           "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.customerId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
           "(:churnStatus IS NULL OR c.churnStatus = :churnStatus) AND " +
           "(:contract IS NULL OR c.contract = :contract) AND " +
           "(:internetService IS NULL OR c.internetService = :internetService)")
    Page<Customer> searchAndFilter(
        @Param("search") String search,
        @Param("churnStatus") Boolean churnStatus,
        @Param("contract") Customer.Contract contract,
        @Param("internetService") Customer.InternetService internetService,
        Pageable pageable
    );

    // Analytics queries
    @Query("SELECT AVG(c.tenure) FROM Customer c")
    Double findAverageTenure();

    @Query("SELECT AVG(c.monthlyCharges) FROM Customer c")
    Double findAverageMonthlyCharges();

    @Query("SELECT c.contract, COUNT(c) FROM Customer c GROUP BY c.contract")
    List<Object[]> countByContractGrouped();

    @Query("SELECT c.internetService, COUNT(c) FROM Customer c GROUP BY c.internetService")
    List<Object[]> countByInternetServiceGrouped();

    @Query("SELECT c.paymentMethod, COUNT(c) FROM Customer c GROUP BY c.paymentMethod")
    List<Object[]> countByPaymentMethodGrouped();

    @Query("SELECT c.gender, COUNT(c) FROM Customer c GROUP BY c.gender")
    List<Object[]> countByGenderGrouped();

    // Churn analytics
    @Query("SELECT c.contract, c.churnStatus, COUNT(c) FROM Customer c GROUP BY c.contract, c.churnStatus")
    List<Object[]> churnByContract();

    @Query("SELECT c.internetService, c.churnStatus, COUNT(c) FROM Customer c GROUP BY c.internetService, c.churnStatus")
    List<Object[]> churnByInternetService();

    @Query("SELECT c.paymentMethod, c.churnStatus, COUNT(c) FROM Customer c GROUP BY c.paymentMethod, c.churnStatus")
    List<Object[]> churnByPaymentMethod();

    // Tenure buckets for churn analysis
    @Query("""
        SELECT
            CASE
                WHEN c.tenure <= 12 THEN '0-12 months'
                WHEN c.tenure <= 24 THEN '13-24 months'
                WHEN c.tenure <= 36 THEN '25-36 months'
                WHEN c.tenure <= 48 THEN '37-48 months'
                WHEN c.tenure <= 60 THEN '49-60 months'
                ELSE '60+ months'
            END as bucket,
            c.churnStatus,
            COUNT(c)
        FROM Customer c
        GROUP BY bucket, c.churnStatus
        ORDER BY bucket
    """)
    List<Object[]> churnByTenureBucket();

    // Monthly charges buckets
    @Query("""
        SELECT
            CASE
                WHEN c.monthlyCharges < 30 THEN 'Under $30'
                WHEN c.monthlyCharges < 60 THEN '$30-$59'
                WHEN c.monthlyCharges < 90 THEN '$60-$89'
                ELSE '$90+'
            END as bucket,
            c.churnStatus,
            COUNT(c)
        FROM Customer c
        GROUP BY bucket, c.churnStatus
        ORDER BY bucket
    """)
    List<Object[]> churnByMonthlyChargesBucket();

    // Recent customers
    List<Customer> findTop10ByOrderByCreatedAtDesc();

    // High-risk customers (based on latest prediction)
    @Query("""
        SELECT DISTINCT c FROM Customer c
        JOIN c.predictions p
        WHERE p.riskCategory = 'HIGH'
        AND p.createdAt = (
            SELECT MAX(p2.createdAt) FROM Prediction p2 WHERE p2.customer = c
        )
        ORDER BY c.createdAt DESC
    """)
    List<Customer> findHighRiskCustomers();

    @Query("SELECT COUNT(DISTINCT c) FROM Customer c JOIN c.predictions p WHERE p.riskCategory = 'HIGH'")
    long countHighRiskCustomers();
}
