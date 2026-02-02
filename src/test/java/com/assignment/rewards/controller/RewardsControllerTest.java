package com.assignment.rewards.controller;

import com.assignment.rewards.model.RewardsResponse;
import com.assignment.rewards.service.RewardsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.YearMonth;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RewardsControllerTest {

    @Mock
    private RewardsService rewardsService;

    @InjectMocks
    private RewardsController rewardsController;

    @Test
    void getRewards_shouldReturnRewardsResponse() {

        String customerId = "C001";

        RewardsResponse expectedResponse = new RewardsResponse(customerId,
                Map.of(YearMonth.of(2024, 1), 120, YearMonth.of(2024, 2), 30), 150);

        when(rewardsService.calculateRewards(customerId)).thenReturn(expectedResponse);

        RewardsResponse actualResponse = rewardsController.getRewards(customerId);

        assertEquals(expectedResponse.getCustomerId(), actualResponse.getCustomerId());
        assertEquals(expectedResponse.getTotalRewards(), actualResponse.getTotalRewards());
        assertEquals(expectedResponse.getMonthlyRewards(), actualResponse.getMonthlyRewards());
    }

    @Test
    void getRewardsForAllCustomers_shouldReturnRewardsForAllCustomers() {

        // given
        RewardsResponse c001Response =
                new RewardsResponse("C001", Map.of(YearMonth.of(2024, 1), 120), 120);

        RewardsResponse c002Response =
                new RewardsResponse("C002", Map.of(YearMonth.of(2024, 2), 90), 90);

        Map<String, RewardsResponse> expectedResponse =
                Map.of("C001", c001Response, "C002", c002Response);

        when(rewardsService.calculateRewardsForAllCustomers()).thenReturn(expectedResponse);

        // when
        Map<String, RewardsResponse> actualResponse = rewardsController.getRewardsForAllCustomers();

        // then
        assertEquals(2, actualResponse.size());

        assertEquals("C001", actualResponse.get("C001").getCustomerId());
        assertEquals(120, actualResponse.get("C001").getTotalRewards());
        assertEquals(Map.of(YearMonth.of(2024, 1), 120),
                actualResponse.get("C001").getMonthlyRewards());

        assertEquals("C002", actualResponse.get("C002").getCustomerId());
        assertEquals(90, actualResponse.get("C002").getTotalRewards());
        assertEquals(Map.of(YearMonth.of(2024, 2), 90), actualResponse.get("C002").getMonthlyRewards());
    }


}
