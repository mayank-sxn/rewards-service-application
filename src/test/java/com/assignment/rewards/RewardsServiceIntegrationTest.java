package com.assignment.rewards;

import com.assignment.rewards.controller.RewardsController;
import com.assignment.rewards.model.RewardsResponse;
import com.assignment.rewards.service.RewardsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.YearMonth;
import java.util.Map;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for RewardsController.
 */
@WebMvcTest(RewardsController.class)
class RewardsServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @Test
    void shouldReturnRewardsResponse_whenCustomerExists() throws Exception {

        RewardsResponse response = new RewardsResponse("testCustomer",
                Map.of(YearMonth.of(2024, 1), 120, YearMonth.of(2024, 2), 10), 130);

        when(rewardsService.calculateRewards("testCustomer")).thenReturn(response);

        mockMvc.perform(get("/api/rewards/testCustomer")).andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("testCustomer"))
                .andExpect(jsonPath("$.monthlyRewards").exists())
                .andExpect(jsonPath("$.totalRewards").value(130));
    }


    @Test
    void shouldReturnZeroRewards_whenNoTransactionsExist() throws Exception {

        RewardsResponse response = new RewardsResponse("emptyCustomer", Map.of(), 0);

        when(rewardsService.calculateRewards("emptyCustomer")).thenReturn(response);

        mockMvc.perform(get("/api/rewards/emptyCustomer")).andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value("emptyCustomer"))
                .andExpect(jsonPath("$.monthlyRewards").isEmpty())
                .andExpect(jsonPath("$.totalRewards").value(0));
    }
}
