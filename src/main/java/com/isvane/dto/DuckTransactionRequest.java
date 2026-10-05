package com.isvane.dto;

import jakarta.validation.constraints.Min;

public record DuckTransactionRequest(
    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity
) {}
