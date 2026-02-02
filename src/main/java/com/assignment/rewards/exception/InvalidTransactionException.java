package com.assignment.rewards.exception;

/**
 * Thrown when an invalid transaction is encountered.
 */
public class InvalidTransactionException extends RuntimeException {

    public InvalidTransactionException(String message) {
        super(message);
    }
}
