package com.assignment.rewards.repository;

import com.assignment.rewards.exception.CustomerNotFoundException;
import com.assignment.rewards.model.Transaction;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionRepository {

    private final Map<String, List<Transaction>> customerTransactions = new HashMap<>();

    @PostConstruct
    public void loadData() {

        customerTransactions.put("C001",
                List.of(new Transaction("C001", BigDecimal.valueOf(120), LocalDate.now().minusMonths(2)),
                        new Transaction("C001", BigDecimal.valueOf(75), LocalDate.now().minusMonths(2)),
                        new Transaction("C001", BigDecimal.valueOf(200), LocalDate.now().minusMonths(1))));

        customerTransactions.put("C002",
                List.of(new Transaction("C002", BigDecimal.valueOf(50), LocalDate.now().minusMonths(1)),
                        new Transaction("C002", BigDecimal.valueOf(130), LocalDate.now())));

        customerTransactions.put("C003",
                List.of(new Transaction("C003", BigDecimal.valueOf(90), LocalDate.now().minusMonths(2)),
                        new Transaction("C003", BigDecimal.valueOf(220), LocalDate.now())));
    }

    public List<Transaction> findTransactionsByCustomerId(String customerId) {

        List<Transaction> transactions = customerTransactions.get(customerId);

        if (transactions == null) {
            throw new CustomerNotFoundException(customerId);
        }

        return transactions;
    }


    public Map<String, List<Transaction>> findAllTransactions() {
        return customerTransactions;
    }
}
