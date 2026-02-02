package com.assignment.rewards.service;

import com.assignment.rewards.exception.InvalidTransactionException;
import com.assignment.rewards.model.RewardsResponse;
import com.assignment.rewards.model.Transaction;
import com.assignment.rewards.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardsServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private RewardsService rewardsService;

    @Test
    void calculatePoints_amountAbove100_shouldReturnCorrectPoints() {
        int points = rewardsService.calculatePoints(BigDecimal.valueOf(120));
        assertEquals(90, points);
    }

    @Test
    void calculatePoints_between50And100_shouldReturnCorrectPoints() {
        int points = rewardsService.calculatePoints(BigDecimal.valueOf(80));
        assertEquals(30, points);
    }

    @Test
    void calculatePoints_below50_shouldReturnZero() {
        int points = rewardsService.calculatePoints(BigDecimal.valueOf(40));
        assertEquals(0, points);
    }

    @Test
    void calculatePoints_nullAmount_shouldThrowException() {
        assertThrows(InvalidTransactionException.class,
                () -> rewardsService.calculatePoints(null));
    }

    @Test
    void calculatePoints_negativeAmount_shouldThrowException() {
        assertThrows(InvalidTransactionException.class,
                () -> rewardsService.calculatePoints(BigDecimal.valueOf(-10)));
    }

    @Test
    void calculateRewards_shouldReturnMonthlyAndTotalRewards() {

        String customerId = "C123";

        Transaction tx1 =
                new Transaction(customerId, BigDecimal.valueOf(120), LocalDate.of(2024, 1, 10));

        Transaction tx2 =
                new Transaction(customerId, BigDecimal.valueOf(80), LocalDate.of(2024, 1, 20));

        Transaction tx3 = new Transaction(customerId, BigDecimal.valueOf(60), LocalDate.of(2024, 2, 5));

        when(transactionRepository.findTransactionsByCustomerId(customerId))
                .thenReturn(List.of(tx1, tx2, tx3));

        RewardsResponse response = rewardsService.calculateRewards(customerId);

        Map<YearMonth, Integer> monthlyRewards = response.getMonthlyRewards();

        assertEquals(2, monthlyRewards.size());
        assertEquals(Integer.valueOf(120), monthlyRewards.get(YearMonth.of(2024, 1)));
        assertEquals(Integer.valueOf(10), monthlyRewards.get(YearMonth.of(2024, 2)));
        assertEquals(130, response.getTotalRewards());
    }

    @Test
    void shouldCalculateRewardsForAllCustomers() {

        // given
        Map<String, List<Transaction>> mockData = Map.of("C001",
                List.of(new Transaction("C001", BigDecimal.valueOf(120), LocalDate.of(2025, 12, 10)),
                        new Transaction("C001", BigDecimal.valueOf(75), LocalDate.of(2025, 12, 15))),
                "C002",
                List.of(new Transaction("C002", BigDecimal.valueOf(200), LocalDate.of(2026, 1, 5))));

        when(transactionRepository.findAllTransactions()).thenReturn(mockData);

        // when
        Map<String, RewardsResponse> result = rewardsService.calculateRewardsForAllCustomers();

        // then
        assertEquals(2, result.size());

        RewardsResponse c001Rewards = result.get("C001");
        assertNotNull(c001Rewards);
        assertEquals("C001", c001Rewards.getCustomerId());

        Map<YearMonth, Integer> c001Monthly = c001Rewards.getMonthlyRewards();
        assertEquals(1, c001Monthly.size());
        assertEquals(115, c001Rewards.getTotalRewards());
        // 120 -> 90 points, 75 -> 25 points, total = 115

        RewardsResponse c002Rewards = result.get("C002");
        assertEquals(250, c002Rewards.getTotalRewards());
        // 200 -> 250 points
    }
}
