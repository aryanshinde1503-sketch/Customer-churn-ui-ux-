package com.churnprediction.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for Model Information and Settings pages.
 */
@Controller
public class InfoController {

    @GetMapping("/model-info")
    public String modelInfo(Model model) {
        model.addAttribute("activePage", "model-info");
        return "model-info";
    }

    @GetMapping("/settings")
    public String settings(Model model) {
        model.addAttribute("activePage", "settings");
        return "settings";
    }

    @PostMapping("/settings/save")
    public String saveSettings(
            @RequestParam(required = false) String theme,
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String email,
            RedirectAttributes redirectAttributes) {
        // In a full app this would persist to DB/session
        redirectAttributes.addFlashAttribute("successMessage", "Settings saved successfully!");
        return "redirect:/settings";
    }
}
