package com.churnprediction.app.config;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.churnprediction.app.dto.PredictionFormDto;
import com.churnprediction.app.entity.Customer;
import com.churnprediction.app.entity.Prediction;
import com.churnprediction.app.repository.CustomerRepository;
import com.churnprediction.app.repository.PredictionRepository;
import com.churnprediction.app.service.DemoPredictionService;

/**
 * Database seeder that creates realistic demonstration records on startup.
 * All records are clearly marked as demo data (isDemoData = true).
 */
@Configuration
public class DataSeeder {

    private static final Logger log = LoggerFactory.getLogger(DataSeeder.class);

    private final CustomerRepository customerRepository;
    private final PredictionRepository predictionRepository;
    private final DemoPredictionService demoPredictionService;

    public DataSeeder(CustomerRepository customerRepository,
                      PredictionRepository predictionRepository,
                      DemoPredictionService demoPredictionService) {
        this.customerRepository = customerRepository;
        this.predictionRepository = predictionRepository;
        this.demoPredictionService = demoPredictionService;
    }

    @Bean
    public ApplicationRunner seedDatabase() {
        return args -> {
            if (customerRepository.count() > 0) {
                log.info("Database already seeded. Skipping seeding.");
                return;
            }

            log.info("Seeding database with demonstration records...");
            List<Customer> customers = createDemoCustomers();
            customerRepository.saveAll(customers);
            log.info("Seeded {} demo customers.", customers.size());

            // Generate predictions for all demo customers
            int predCount = 0;
            for (Customer c : customers) {
                PredictionFormDto form = buildFormFromCustomer(c);
                double prob = demoPredictionService.computeChurnProbability(form);
                Prediction.RiskCategory category = demoPredictionService.categorize(prob);

                Prediction p = Prediction.builder()
                    .customer(c)
                    .churnProbability(demoPredictionService.roundedProbability(prob))
                    .riskCategory(category)
                    .predictionMethod(Prediction.PredictionMethod.DEMO)
                    .notes("Initial demo prediction generated on seeding.")
                    .build();
                predictionRepository.save(p);
                predCount++;
            }
            log.info("Seeded {} demo predictions.", predCount);
            log.info("✅ Database seeding complete. Application is ready.");
        };
    }

    private PredictionFormDto buildFormFromCustomer(Customer c) {
        PredictionFormDto form = new PredictionFormDto();
        form.setGender(c.getGender());
        form.setSeniorCitizen(c.getSeniorCitizen());
        form.setPartner(c.getPartner());
        form.setDependents(c.getDependents());
        form.setTenure(c.getTenure());
        form.setPhoneService(c.getPhoneService());
        form.setMultipleLines(c.getMultipleLines());
        form.setInternetService(c.getInternetService());
        form.setOnlineSecurity(c.getOnlineSecurity());
        form.setOnlineBackup(c.getOnlineBackup());
        form.setDeviceProtection(c.getDeviceProtection());
        form.setTechSupport(c.getTechSupport());
        form.setStreamingTv(c.getStreamingTv());
        form.setStreamingMovies(c.getStreamingMovies());
        form.setContract(c.getContract());
        form.setPaperlessBilling(c.getPaperlessBilling());
        form.setPaymentMethod(c.getPaymentMethod());
        form.setMonthlyCharges(c.getMonthlyCharges());
        form.setTotalCharges(c.getTotalCharges());
        return form;
    }

    private List<Customer> createDemoCustomers() {
        return List.of(
            buildCustomer("CUST-001","Alice","Johnson","alice.j@email.com", Customer.Gender.FEMALE, false,true,false,
                12,true, Customer.ServiceStatus.NO, Customer.InternetService.FIBER_OPTIC,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.YES, Customer.ServiceStatus.YES,
                Customer.Contract.MONTH_TO_MONTH,true, Customer.PaymentMethod.ELECTRONIC_CHECK,
                new BigDecimal("89.10"), new BigDecimal("1069.20"), true),

            buildCustomer("CUST-002","Bob","Martinez","bob.m@email.com", Customer.Gender.MALE, false,false,false,
                34,true, Customer.ServiceStatus.YES, Customer.InternetService.FIBER_OPTIC,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.NO, Customer.ServiceStatus.YES,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.YES, Customer.ServiceStatus.NO,
                Customer.Contract.ONE_YEAR,true, Customer.PaymentMethod.CREDIT_CARD,
                new BigDecimal("74.45"), new BigDecimal("2531.30"), false),

            buildCustomer("CUST-003","Carol","Williams","carol.w@email.com", Customer.Gender.FEMALE, false,true,true,
                60,true, Customer.ServiceStatus.NO, Customer.InternetService.DSL,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.YES, Customer.ServiceStatus.YES,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.Contract.TWO_YEAR,false, Customer.PaymentMethod.BANK_TRANSFER,
                new BigDecimal("54.00"), new BigDecimal("3240.00"), false),

            buildCustomer("CUST-004","David","Brown","david.b@email.com", Customer.Gender.MALE, true,false,false,
                3,true, Customer.ServiceStatus.NO_PHONE_SERVICE, Customer.InternetService.FIBER_OPTIC,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.Contract.MONTH_TO_MONTH,true, Customer.PaymentMethod.ELECTRONIC_CHECK,
                new BigDecimal("70.35"), new BigDecimal("211.05"), true),

            buildCustomer("CUST-005","Emma","Davis","emma.d@email.com", Customer.Gender.FEMALE, false,true,true,
                72,false, Customer.ServiceStatus.NO_PHONE_SERVICE, Customer.InternetService.DSL,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.YES, Customer.ServiceStatus.NO,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.Contract.TWO_YEAR,false, Customer.PaymentMethod.MAILED_CHECK,
                new BigDecimal("39.65"), new BigDecimal("2854.80"), false),

            buildCustomer("CUST-006","Frank","Garcia","frank.g@email.com", Customer.Gender.MALE, false,false,false,
                5,true, Customer.ServiceStatus.NO, Customer.InternetService.FIBER_OPTIC,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.YES, Customer.ServiceStatus.YES,
                Customer.Contract.MONTH_TO_MONTH,true, Customer.PaymentMethod.ELECTRONIC_CHECK,
                new BigDecimal("95.45"), new BigDecimal("477.25"), true),

            buildCustomer("CUST-007","Grace","Miller","grace.m@email.com", Customer.Gender.FEMALE, false,true,false,
                48,true, Customer.ServiceStatus.YES, Customer.InternetService.DSL,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.YES, Customer.ServiceStatus.YES,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.Contract.ONE_YEAR,true, Customer.PaymentMethod.CREDIT_CARD,
                new BigDecimal("59.95"), new BigDecimal("2877.60"), false),

            buildCustomer("CUST-008","Henry","Wilson","henry.w@email.com", Customer.Gender.MALE, true,false,false,
                2,true, Customer.ServiceStatus.NO, Customer.InternetService.FIBER_OPTIC,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.YES, Customer.ServiceStatus.NO,
                Customer.Contract.MONTH_TO_MONTH,true, Customer.PaymentMethod.MAILED_CHECK,
                new BigDecimal("78.70"), new BigDecimal("157.40"), true),

            buildCustomer("CUST-009","Iris","Moore","iris.m@email.com", Customer.Gender.FEMALE, false,true,true,
                65,true, Customer.ServiceStatus.YES, Customer.InternetService.NO,
                Customer.ServiceStatus.NO_INTERNET_SERVICE, Customer.ServiceStatus.NO_INTERNET_SERVICE,
                Customer.ServiceStatus.NO_INTERNET_SERVICE, Customer.ServiceStatus.NO_INTERNET_SERVICE,
                Customer.ServiceStatus.NO_INTERNET_SERVICE, Customer.ServiceStatus.NO_INTERNET_SERVICE,
                Customer.Contract.TWO_YEAR,false, Customer.PaymentMethod.BANK_TRANSFER,
                new BigDecimal("19.85"), new BigDecimal("1290.25"), false),

            buildCustomer("CUST-010","Jack","Taylor","jack.t@email.com", Customer.Gender.MALE, false,false,false,
                9,true, Customer.ServiceStatus.NO, Customer.InternetService.FIBER_OPTIC,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.YES, Customer.ServiceStatus.NO,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.Contract.MONTH_TO_MONTH,false, Customer.PaymentMethod.ELECTRONIC_CHECK,
                new BigDecimal("73.90"), new BigDecimal("665.10"), true),

            buildCustomer("CUST-011","Karen","Anderson","karen.a@email.com", Customer.Gender.FEMALE, false,true,true,
                55,true, Customer.ServiceStatus.NO, Customer.InternetService.DSL,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.YES, Customer.ServiceStatus.YES,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.Contract.TWO_YEAR,false, Customer.PaymentMethod.BANK_TRANSFER,
                new BigDecimal("49.25"), new BigDecimal("2708.75"), false),

            buildCustomer("CUST-012","Liam","Thomas","liam.t@email.com", Customer.Gender.MALE, false,false,true,
                24,true, Customer.ServiceStatus.YES, Customer.InternetService.FIBER_OPTIC,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.NO, Customer.ServiceStatus.YES,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.YES, Customer.ServiceStatus.YES,
                Customer.Contract.ONE_YEAR,true, Customer.PaymentMethod.CREDIT_CARD,
                new BigDecimal("108.15"), new BigDecimal("2595.60"), false),

            buildCustomer("CUST-013","Mary","Jackson","mary.j@email.com", Customer.Gender.FEMALE, true,false,false,
                1,true, Customer.ServiceStatus.NO, Customer.InternetService.FIBER_OPTIC,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.Contract.MONTH_TO_MONTH,true, Customer.PaymentMethod.ELECTRONIC_CHECK,
                new BigDecimal("68.80"), new BigDecimal("68.80"), true),

            buildCustomer("CUST-014","Nathan","White","nathan.w@email.com", Customer.Gender.MALE, false,true,false,
                40,true, Customer.ServiceStatus.YES, Customer.InternetService.DSL,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.YES, Customer.ServiceStatus.NO,
                Customer.Contract.ONE_YEAR,false, Customer.PaymentMethod.MAILED_CHECK,
                new BigDecimal("62.50"), new BigDecimal("2500.00"), false),

            buildCustomer("CUST-015","Olivia","Harris","olivia.h@email.com", Customer.Gender.FEMALE, false,true,true,
                70,true, Customer.ServiceStatus.YES, Customer.InternetService.FIBER_OPTIC,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.YES, Customer.ServiceStatus.YES,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.YES, Customer.ServiceStatus.YES,
                Customer.Contract.TWO_YEAR,true, Customer.PaymentMethod.CREDIT_CARD,
                new BigDecimal("111.50"), new BigDecimal("7805.00"), false),

            buildCustomer("CUST-016","Peter","Martin","peter.m@email.com", Customer.Gender.MALE, false,false,false,
                7,true, Customer.ServiceStatus.NO, Customer.InternetService.FIBER_OPTIC,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.YES, Customer.ServiceStatus.YES,
                Customer.Contract.MONTH_TO_MONTH,true, Customer.PaymentMethod.ELECTRONIC_CHECK,
                new BigDecimal("93.25"), new BigDecimal("652.75"), true),

            buildCustomer("CUST-017","Quinn","Thompson","quinn.t@email.com", Customer.Gender.OTHER, false,false,false,
                30,true, Customer.ServiceStatus.NO, Customer.InternetService.NO,
                Customer.ServiceStatus.NO_INTERNET_SERVICE, Customer.ServiceStatus.NO_INTERNET_SERVICE,
                Customer.ServiceStatus.NO_INTERNET_SERVICE, Customer.ServiceStatus.NO_INTERNET_SERVICE,
                Customer.ServiceStatus.NO_INTERNET_SERVICE, Customer.ServiceStatus.NO_INTERNET_SERVICE,
                Customer.Contract.ONE_YEAR,false, Customer.PaymentMethod.BANK_TRANSFER,
                new BigDecimal("25.10"), new BigDecimal("753.00"), false),

            buildCustomer("CUST-018","Rachel","Garcia","rachel.g@email.com", Customer.Gender.FEMALE, false,true,false,
                15,true, Customer.ServiceStatus.YES, Customer.InternetService.FIBER_OPTIC,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.YES, Customer.ServiceStatus.NO,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.NO, Customer.ServiceStatus.YES,
                Customer.Contract.MONTH_TO_MONTH,true, Customer.PaymentMethod.ELECTRONIC_CHECK,
                new BigDecimal("84.95"), new BigDecimal("1274.25"), true),

            buildCustomer("CUST-019","Samuel","Martinez","samuel.m@email.com", Customer.Gender.MALE, false,true,true,
                58,true, Customer.ServiceStatus.YES, Customer.InternetService.DSL,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.YES, Customer.ServiceStatus.YES,
                Customer.ServiceStatus.YES, Customer.ServiceStatus.YES, Customer.ServiceStatus.YES,
                Customer.Contract.TWO_YEAR,false, Customer.PaymentMethod.CREDIT_CARD,
                new BigDecimal("79.40"), new BigDecimal("4605.20"), false),

            buildCustomer("CUST-020","Tina","Robinson","tina.r@email.com", Customer.Gender.FEMALE, true,false,false,
                4,false, Customer.ServiceStatus.NO_PHONE_SERVICE, Customer.InternetService.FIBER_OPTIC,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.ServiceStatus.NO, Customer.ServiceStatus.NO, Customer.ServiceStatus.NO,
                Customer.Contract.MONTH_TO_MONTH,true, Customer.PaymentMethod.ELECTRONIC_CHECK,
                new BigDecimal("45.00"), new BigDecimal("180.00"), true)
        );
    }

    private Customer buildCustomer(String custId, String first, String last, String email,
                                    Customer.Gender gender, boolean senior, boolean partner, boolean deps,
                                    int tenure, boolean phone, Customer.ServiceStatus multiLines,
                                    Customer.InternetService internet,
                                    Customer.ServiceStatus onlineSec, Customer.ServiceStatus onlineBack,
                                    Customer.ServiceStatus deviceProt, Customer.ServiceStatus techSup,
                                    Customer.ServiceStatus streamTv, Customer.ServiceStatus streamMov,
                                    Customer.Contract contract, boolean paperless,
                                    Customer.PaymentMethod payment,
                                    BigDecimal monthly, BigDecimal total, boolean churned) {
        return Customer.builder()
            .customerId(custId)
            .firstName(first)
            .lastName(last)
            .email(email)
            .gender(gender)
            .seniorCitizen(senior)
            .partner(partner)
            .dependents(deps)
            .tenure(tenure)
            .phoneService(phone)
            .multipleLines(multiLines)
            .internetService(internet)
            .onlineSecurity(onlineSec)
            .onlineBackup(onlineBack)
            .deviceProtection(deviceProt)
            .techSupport(techSup)
            .streamingTv(streamTv)
            .streamingMovies(streamMov)
            .contract(contract)
            .paperlessBilling(paperless)
            .paymentMethod(payment)
            .monthlyCharges(monthly)
            .totalCharges(total)
            .churnStatus(churned)
            .isDemoData(true)
            .build();
    }
}
