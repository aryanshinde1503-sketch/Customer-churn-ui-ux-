package com.churnprediction.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.churnprediction.app.service.DashboardService;

/**
 * Controller for the main dashboard page.
 */
@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("stats", dashboardService.getDashboardStats());
        model.addAttribute("recentCustomers", dashboardService.getRecentCustomers());
        model.addAttribute("recentPredictions", dashboardService.getRecentPredictions());
        model.addAttribute("activePage", "dashboard");
        return "dashboard";
    }
}
