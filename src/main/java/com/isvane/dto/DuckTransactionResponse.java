package com.isvane.dto;

public record DuckTransactionResponse(
    boolean success,
    String message,
    int userDucks,
    int storeDucks
) {
    public static DuckTransactionResponse ok(String message, int userDucks, int storeDucks) {
        return new DuckTransactionResponse(true, message, userDucks, storeDucks);
    }

    public static DuckTransactionResponse error(String message, int userDucks, int storeDucks) {
        return new DuckTransactionResponse(false, message, userDucks, storeDucks);
    }
}
