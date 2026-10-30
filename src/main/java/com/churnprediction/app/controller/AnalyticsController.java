package com.churnprediction.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.churnprediction.app.service.AnalyticsService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controller for Analytics charts page.
 */
@Controller
@RequestMapping("/analytics")
public class AnalyticsController {

    private static final Logger log = LoggerFactory.getLogger(AnalyticsController.class);

    private final AnalyticsService analyticsService;
    private final ObjectMapper objectMapper;

    public AnalyticsController(AnalyticsService analyticsService, ObjectMapper objectMapper) {
        this.analyticsService = analyticsService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public String analyticsPage(Model model) {
        try {
            model.addAttribute("churnDistJson",
                objectMapper.writeValueAsString(analyticsService.getChurnDistribution()));
            model.addAttribute("churnByContractJson",
                objectMapper.writeValueAsString(analyticsService.getChurnByContract()));
            model.addAttribute("churnByInternetJson",
                objectMapper.writeValueAsString(analyticsService.getChurnByInternetService()));
            model.addAttribute("churnByPaymentJson",
                objectMapper.writeValueAsString(analyticsService.getChurnByPaymentMethod()));
            model.addAttribute("churnByTenureJson",
                objectMapper.writeValueAsString(analyticsService.getChurnByTenure()));
            model.addAttribute("churnByChargesJson",
                objectMapper.writeValueAsString(analyticsService.getChurnByMonthlyCharges()));
            model.addAttribute("contractDistJson",
                objectMapper.writeValueAsString(analyticsService.getContractDistribution()));
            model.addAttribute("genderDistJson",
                objectMapper.writeValueAsString(analyticsService.getGenderDistribution()));
        } catch (Exception e) {
            log.error("Error serializing analytics data", e);
        }
        model.addAttribute("activePage", "analytics");
        return "analytics";
    }
}
