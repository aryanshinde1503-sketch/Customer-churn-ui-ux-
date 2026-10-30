package com.churnprediction.app.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for MVC controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CustomerNotFoundException.class)
    public String handleCustomerNotFound(CustomerNotFoundException ex, Model model) {
        log.warn("Customer not found: {}", ex.getMessage());
        model.addAttribute("errorTitle", "Customer Not Found");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", 404);
        return "error";
    }

    @ExceptionHandler(DuplicateCustomerIdException.class)
    public String handleDuplicateCustomerId(DuplicateCustomerIdException ex, Model model) {
        log.warn("Duplicate customer ID: {}", ex.getMessage());
        model.addAttribute("errorTitle", "Duplicate Customer ID");
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", 409);
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        model.addAttribute("errorTitle", "An Unexpected Error Occurred");
        model.addAttribute("errorMessage", "Please try again or contact support.");
        model.addAttribute("errorCode", 500);
        return "error";
    }
}
