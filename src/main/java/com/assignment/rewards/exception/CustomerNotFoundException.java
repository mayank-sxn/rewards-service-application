package com.assignment.rewards.exception;

/**
 * Thrown when a customerId does not exist in the system.
 */
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(String customerId) {
        super("Customer not found with id: " + customerId);
    }
}
