package com.churnprediction.app.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.churnprediction.app.dto.PredictionDto;
import com.churnprediction.app.dto.PredictionFormDto;
import com.churnprediction.app.entity.Customer;
import com.churnprediction.app.entity.Prediction;
import com.churnprediction.app.exception.CustomerNotFoundException;
import com.churnprediction.app.service.CustomerService;
import com.churnprediction.app.service.PredictionService;

/**
 * Controller for Prediction operations including form, result, history.
 */
@Controller
@RequestMapping("/predictions")
public class PredictionController {

    private static final Logger log = LoggerFactory.getLogger(PredictionController.class);

    private final PredictionService predictionService;
    private final CustomerService customerService;

    public PredictionController(PredictionService predictionService, CustomerService customerService) {
        this.predictionService = predictionService;
        this.customerService = customerService;
    }

    // Show prediction form
    @GetMapping("/new")
    public String showPredictionForm(
            @RequestParam(required = false) Long customerId,
            Model model) {

        PredictionFormDto form = new PredictionFormDto();
        List<Customer> customers = customerService.findAll();

        // Pre-populate if customer is selected
        if (customerId != null) {
            Customer customer = customerService.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + customerId));
            form.setExistingCustomerId(customerId);
            form.setGender(customer.getGender());
            form.setSeniorCitizen(customer.getSeniorCitizen());
            form.setPartner(customer.getPartner());
            form.setDependents(customer.getDependents());
            form.setTenure(customer.getTenure());
            form.setPhoneService(customer.getPhoneService());
            form.setMultipleLines(customer.getMultipleLines());
            form.setInternetService(customer.getInternetService());
            form.setOnlineSecurity(customer.getOnlineSecurity());
            form.setOnlineBackup(customer.getOnlineBackup());
            form.setDeviceProtection(customer.getDeviceProtection());
            form.setTechSupport(customer.getTechSupport());
            form.setStreamingTv(customer.getStreamingTv());
            form.setStreamingMovies(customer.getStreamingMovies());
            form.setContract(customer.getContract());
            form.setPaperlessBilling(customer.getPaperlessBilling());
            form.setPaymentMethod(customer.getPaymentMethod());
            form.setMonthlyCharges(customer.getMonthlyCharges());
            form.setTotalCharges(customer.getTotalCharges());
            model.addAttribute("selectedCustomer", customer);
        }

        model.addAttribute("predictionForm", form);
        model.addAttribute("customers", customers);
        model.addAttribute("genders", Customer.Gender.values());
        model.addAttribute("serviceStatuses", Customer.ServiceStatus.values());
        model.addAttribute("internetServices", Customer.InternetService.values());
        model.addAttribute("contracts", Customer.Contract.values());
        model.addAttribute("paymentMethods", Customer.PaymentMethod.values());
        model.addAttribute("activePage", "predictions");
        return "prediction/form";
    }

    // Run prediction and show result
    @PostMapping("/run")
    public String runPrediction(@ModelAttribute PredictionFormDto form, Model model) {
        Long customerId = form.getExistingCustomerId();

        PredictionDto result = predictionService.predict(form, customerId);

        // Store form and result in model for result page
        model.addAttribute("result", result);
        model.addAttribute("form", form);
        model.addAttribute("activePage", "predictions");

        if (customerId != null) {
            customerService.findById(customerId).ifPresent(c ->
                model.addAttribute("customer", c));
        }
        return "prediction/result";
    }

    // Save prediction to database
    @PostMapping("/save")
    public String savePrediction(
            @RequestParam Long customerId,
            @RequestParam java.math.BigDecimal churnProbability,
            @RequestParam Prediction.RiskCategory riskCategory,
            @RequestParam(required = false) String notes,
            RedirectAttributes redirectAttributes) {

        PredictionDto dto = PredictionDto.builder()
            .customerId(customerId)
            .churnProbability(churnProbability)
            .probabilityPercent((int) Math.round(churnProbability.doubleValue() * 100))
            .riskCategory(riskCategory)
            .predictionMethod(Prediction.PredictionMethod.DEMO)
            .notes(notes)
            .build();

        Prediction saved = predictionService.savePrediction(dto, customerId);
        redirectAttributes.addFlashAttribute("successMessage",
            "Prediction saved successfully! ID: #" + saved.getId());
        return "redirect:/predictions/history";
    }

    // Prediction history list
    @GetMapping("/history")
    public String predictionHistory(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) Prediction.RiskCategory riskCategory,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir,
            Model model) {

        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<Prediction> predPage = predictionService.searchAndFilter(search, riskCategory, pageable);

        model.addAttribute("predictions", predPage);
        model.addAttribute("search", search);
        model.addAttribute("riskCategory", riskCategory);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("riskCategories", Prediction.RiskCategory.values());
        model.addAttribute("activePage", "history");

        // Summary stats
        model.addAttribute("totalPredictions", predictionService.countTotal());
        model.addAttribute("highCount", predictionService.countByRiskCategory(Prediction.RiskCategory.HIGH));
        model.addAttribute("mediumCount", predictionService.countByRiskCategory(Prediction.RiskCategory.MEDIUM));
        model.addAttribute("lowCount", predictionService.countByRiskCategory(Prediction.RiskCategory.LOW));
        return "prediction/history";
    }

    // View single prediction details
    @GetMapping("/history/{id}")
    public String viewPrediction(@PathVariable Long id, Model model) {
        Prediction prediction = predictionService.findById(id)
            .orElseThrow(() -> new RuntimeException("Prediction not found: " + id));
        model.addAttribute("prediction", prediction);
        model.addAttribute("activePage", "history");
        return "prediction/detail";
    }
}
