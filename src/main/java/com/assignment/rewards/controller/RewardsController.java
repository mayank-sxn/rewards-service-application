package com.assignment.rewards.controller;

import com.assignment.rewards.model.RewardsResponse;
import com.assignment.rewards.service.RewardsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * REST controller for reward calculation APIs.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardsController {

    private static final Logger logger = LoggerFactory.getLogger(RewardsController.class);

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    /**
     * Returns reward points per month and total for a given customer.
     */
    @GetMapping("/{customerId}")
    public RewardsResponse getRewards(@PathVariable String customerId) {
        logger.info("Received rewards request for customerId={}", customerId);
        return rewardsService.calculateRewards(customerId);
    }

    /**
     * Returns reward points per month and total for all the customers.
     */
    @GetMapping("/all")
    public Map<String, RewardsResponse> getRewardsForAllCustomers() {
        return rewardsService.calculateRewardsForAllCustomers();
    }
}
