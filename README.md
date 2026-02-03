# Rewards Service

A Spring Boot application that calculates reward points for customers based on their purchase transactions.

This project demonstrates clean Java coding standards, REST API design, unit testing, and exception handling using modern Spring Boot and Java.

---

## Problem Statement

A retailer offers a rewards program to its customers:

- **2 points** for every dollar spent **over $100**
- **1 point** for every dollar spent **between $50 and $100**
- Rewards are calculated **per transaction**
- Monthly and total reward points must be calculated
- Months must **not be hard-coded**

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.2.5**
- **Maven**
- **JUnit 5**
- **Mockito**
- **IntelliJ IDEA**

---

## Project Structure
``` text
rewards-service
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.assignment.rewards
│   │   │       ├── RewardsServiceApplication.java
│   │   │       ├── controller
│   │   │       │   └── RewardsController.java
│   │   │       ├── service
│   │   │       │   └── RewardsService.java
│   │   │       ├── repository
│   │   │       │   └── TransactionRepository.java
│   │   │       ├── model
│   │   │       │   ├── Transaction.java
│   │   │       │   └── RewardsResponse.java
│   │   │       └── exception
│   │   │           ├── InvalidTransactionException.java
│   │   │           ├── CustomerNotFoundException.java
│   │   │           └── GlobalExceptionHandler.java
│   │   └── resources
│   │       └── application.properties
│   └── test
│       └── java
│           └── com.assignment.rewards
│               ├── controller
│               │   └── RewardsControllerTest.java
│               ├── service
│               │   └── RewardsServiceTest.java
│               ├── RewardsServiceApplicationTests.java
│               └── RewardsServiceIntegrationTest.java
├── pom.xml
├── README.md
└── .gitignore

```
---

## REST API

### Get Rewards for a Customer

**Endpoints**

GET /api/rewards/{customerId}
GET /api/rewards/all

**Example**

GET http://localhost:8080/api/rewards/C001
GET http://localhost:8080/api/rewards/all


**Sample Response**
``` text
{
    "customerId": "C001",
    "monthlyRewards": {
        "2025-12": 115,
        "2026-01": 250
    },
    "totalRewards": 365
}
```

## Reward Calculation Logic

- Transactions are processed individually
- Reward points are calculated per transaction
- Transactions are grouped dynamically by month using YearMonth
- No months are hard-coded
- Negative or null transaction amounts throw a custom exception

## Testing

- Unit tests for reward calculation logic
- Controller tests using MockitoExtension
- Negative scenarios covered
- Global exception handling implemented

## Running the Application
Prerequisites
- Java 21
- Maven 3.9+

Application will start at: http://localhost:8080


## Error Handling
- Custom exception InvalidTransactionException and CustomerNotFoundException
- Centralized exception handling using @RestControllerAdvice
- Clean JSON error responses with appropriate HTTP status codes

👤 Author

Mayank Saxena