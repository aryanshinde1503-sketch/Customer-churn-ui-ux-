package com.churnprediction.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.churnprediction.app.dto.CustomerDto;
import com.churnprediction.app.entity.Customer;
import com.churnprediction.app.exception.CustomerNotFoundException;
import com.churnprediction.app.service.CustomerService;
import com.churnprediction.app.service.PredictionService;

import jakarta.validation.Valid;

/**
 * Controller for Customer CRUD operations.
 */
@Controller
@RequestMapping("/customers")
public class CustomerController {

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;
    private final PredictionService predictionService;

    public CustomerController(CustomerService customerService, PredictionService predictionService) {
        this.customerService = customerService;
        this.predictionService = predictionService;
    }

    @GetMapping
    public String listCustomers(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) Boolean churnStatus,
            @RequestParam(required = false) Customer.Contract contract,
            @RequestParam(required = false) Customer.InternetService internetService,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        Sort sort = sortDir.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        Page<Customer> customerPage = customerService.searchAndFilter(search, churnStatus, contract, internetService, pageable);

        model.addAttribute("customers", customerPage);
        model.addAttribute("search", search);
        model.addAttribute("churnStatus", churnStatus);
        model.addAttribute("contract", contract);
        model.addAttribute("internetService", internetService);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("contracts", Customer.Contract.values());
        model.addAttribute("internetServices", Customer.InternetService.values());
        model.addAttribute("activePage", "customers");
        model.addAttribute("totalCustomers", customerService.countTotal());
        model.addAttribute("churnedCustomers", customerService.countByChurnStatus(true));
        return "customer/list";
    }

    @GetMapping("/{id}")
    public String viewCustomer(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id));
        var predictions = predictionService.findByCustomerId(id);
        model.addAttribute("customer", customer);
        model.addAttribute("predictions", predictions);
        model.addAttribute("activePage", "customers");
        return "customer/view";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customerDto", new CustomerDto());
        model.addAttribute("genders", Customer.Gender.values());
        model.addAttribute("serviceStatuses", Customer.ServiceStatus.values());
        model.addAttribute("internetServices", Customer.InternetService.values());
        model.addAttribute("contracts", Customer.Contract.values());
        model.addAttribute("paymentMethods", Customer.PaymentMethod.values());
        model.addAttribute("activePage", "customers");
        model.addAttribute("editMode", false);
        return "customer/form";
    }

    @PostMapping("/new")
    public String createCustomer(@Valid @ModelAttribute CustomerDto customerDto,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("genders", Customer.Gender.values());
            model.addAttribute("serviceStatuses", Customer.ServiceStatus.values());
            model.addAttribute("internetServices", Customer.InternetService.values());
            model.addAttribute("contracts", Customer.Contract.values());
            model.addAttribute("paymentMethods", Customer.PaymentMethod.values());
            model.addAttribute("activePage", "customers");
            model.addAttribute("editMode", false);
            return "customer/form";
        }
        try {
            Customer created = customerService.createCustomer(customerDto);
            redirectAttributes.addFlashAttribute("successMessage",
                "Customer '" + created.getFullName() + "' created successfully!");
            return "redirect:/customers/" + created.getId();
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("genders", Customer.Gender.values());
            model.addAttribute("serviceStatuses", Customer.ServiceStatus.values());
            model.addAttribute("internetServices", Customer.InternetService.values());
            model.addAttribute("contracts", Customer.Contract.values());
            model.addAttribute("paymentMethods", Customer.PaymentMethod.values());
            model.addAttribute("activePage", "customers");
            model.addAttribute("editMode", false);
            return "customer/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id)
            .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + id));
        model.addAttribute("customerDto", CustomerDto.fromEntity(customer));
        model.addAttribute("customerId", id);
        model.addAttribute("genders", Customer.Gender.values());
        model.addAttribute("serviceStatuses", Customer.ServiceStatus.values());
        model.addAttribute("internetServices", Customer.InternetService.values());
        model.addAttribute("contracts", Customer.Contract.values());
        model.addAttribute("paymentMethods", Customer.PaymentMethod.values());
        model.addAttribute("activePage", "customers");
        model.addAttribute("editMode", true);
        return "customer/form";
    }

    @PostMapping("/{id}/edit")
    public String updateCustomer(@PathVariable Long id,
                                  @Valid @ModelAttribute CustomerDto customerDto,
                                  BindingResult result,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("customerId", id);
            model.addAttribute("genders", Customer.Gender.values());
            model.addAttribute("serviceStatuses", Customer.ServiceStatus.values());
            model.addAttribute("internetServices", Customer.InternetService.values());
            model.addAttribute("contracts", Customer.Contract.values());
            model.addAttribute("paymentMethods", Customer.PaymentMethod.values());
            model.addAttribute("activePage", "customers");
            model.addAttribute("editMode", true);
            return "customer/form";
        }
        try {
            Customer updated = customerService.updateCustomer(id, customerDto);
            redirectAttributes.addFlashAttribute("successMessage",
                "Customer '" + updated.getFullName() + "' updated successfully!");
            return "redirect:/customers/" + id;
        } catch (Exception ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("customerId", id);
            model.addAttribute("genders", Customer.Gender.values());
            model.addAttribute("serviceStatuses", Customer.ServiceStatus.values());
            model.addAttribute("internetServices", Customer.InternetService.values());
            model.addAttribute("contracts", Customer.Contract.values());
            model.addAttribute("paymentMethods", Customer.PaymentMethod.values());
            model.addAttribute("activePage", "customers");
            model.addAttribute("editMode", true);
            return "customer/form";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("successMessage", "Customer deleted successfully.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/customers";
    }
}
