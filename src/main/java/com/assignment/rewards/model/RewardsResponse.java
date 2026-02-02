package com.assignment.rewards.model;

import java.time.YearMonth;
import java.util.Map;

/**
 * Response object holding reward points per month and total.
 */
public class RewardsResponse {

    private final String customerId;
    private final Map<YearMonth, Integer> monthlyRewards;
    private final int totalRewards;

    public RewardsResponse(String customerId, Map<YearMonth, Integer> monthlyRewards,
                           int totalRewards) {
        this.customerId = customerId;
        this.monthlyRewards = monthlyRewards;
        this.totalRewards = totalRewards;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Map<YearMonth, Integer> getMonthlyRewards() {
        return monthlyRewards;
    }

    public int getTotalRewards() {
        return totalRewards;
    }
}
