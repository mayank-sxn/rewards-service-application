package com.assignment.rewards.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents a customer transaction.
 */
public class Transaction {

    private final String customerId;
    private final BigDecimal amount;
    private final LocalDate transactionDate;

    public Transaction(String customerId, BigDecimal amount, LocalDate transactionDate) {
        this.customerId = customerId;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }
}
