package com.assignment.rewards.service;

import com.assignment.rewards.exception.InvalidTransactionException;
import com.assignment.rewards.model.RewardsResponse;
import com.assignment.rewards.model.Transaction;
import com.assignment.rewards.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service responsible for reward point calculations.
 */
@Service
public class RewardsService {

    private static final Logger logger = LoggerFactory.getLogger(RewardsService.class);

    private final TransactionRepository transactionRepository;

    public RewardsService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Method to get transaction details, calculate monthly and total rewards based on it and return
     * the response.
     */
    public RewardsResponse calculateRewards(String customerId) {

        logger.info("Calculating rewards for customerId={}", customerId);

        List<Transaction> transactions = transactionRepository.findTransactionsByCustomerId(customerId);

        Map<YearMonth, Integer> monthlyRewards = calculateMonthlyRewards(transactions);

        int totalRewards = monthlyRewards.values().stream().mapToInt(Integer::intValue).sum();

        logger.info("Total rewards calculated={} for customerId={}", totalRewards, customerId);

        return new RewardsResponse(customerId, monthlyRewards, totalRewards);
    }

    /**
     * Method to calculate rewards for all the customers.
     */
    public Map<String, RewardsResponse> calculateRewardsForAllCustomers() {

        Map<String, List<Transaction>> allTransactions = transactionRepository.findAllTransactions();

        Map<String, RewardsResponse> response = new HashMap<>();

        for (Map.Entry<String, List<Transaction>> entry : allTransactions.entrySet()) {
            String customerId = entry.getKey();
            List<Transaction> transactions = entry.getValue();

            Map<YearMonth, Integer> monthlyRewards = calculateMonthlyRewards(transactions);

            int totalRewards = monthlyRewards.values().stream().mapToInt(Integer::intValue).sum();

            response.put(customerId, new RewardsResponse(customerId, monthlyRewards, totalRewards));
        }

        return response;
    }


    /**
     * Calculates reward points for a single transaction.
     */
    public int calculatePoints(BigDecimal amount) {
        if (amount == null) {
            throw new InvalidTransactionException("Transaction amount cannot be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidTransactionException("Transaction amount cannot be negative");
        }

        int points = 0;

        if (amount.compareTo(BigDecimal.valueOf(100)) > 0) {
            points += amount.subtract(BigDecimal.valueOf(100)).intValue() * 2;
            points += 50;
        } else if (amount.compareTo(BigDecimal.valueOf(50)) > 0) {
            points += amount.subtract(BigDecimal.valueOf(50)).intValue();
        }

        return points;
    }

    /**
     * Calculates monthly reward points grouped by YearMonth.
     */
    public Map<YearMonth, Integer> calculateMonthlyRewards(List<Transaction> transactions) {
        return transactions.stream()
                .collect(Collectors.groupingBy(tx -> YearMonth.from(tx.getTransactionDate()),
                        Collectors.summingInt(tx -> calculatePoints(tx.getAmount()))));
    }
}
