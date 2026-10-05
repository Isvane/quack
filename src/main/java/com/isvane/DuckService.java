package com.isvane;

import com.isvane.dto.DuckTransactionResponse;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class DuckService {

    @ConfigProperty(name = "ducks.number")
    int ducksNum;

    private AtomicInteger storeDucks;
    private final AtomicInteger userDucks = new AtomicInteger(0);

    @PostConstruct
    void init() {
        storeDucks = new AtomicInteger(ducksNum);
    }

    public DuckTransactionResponse buy(int quantity) {
        if (quantity <= 0) {
            return DuckTransactionResponse.error("Quantity must be greater than zero!", getStoreDucks(), getStoreDucks());
        }

        int currentStock;
        int nextStock;

        do {
            currentStock = storeDucks.get();

            if (currentStock < quantity) {
                return DuckTransactionResponse.error("Not enough stock", getUserDucks(), getStoreDucks());
            }
            nextStock = currentStock - quantity;
        } while (!storeDucks.compareAndSet(currentStock, nextStock));

        userDucks.addAndGet(quantity);
        return DuckTransactionResponse.ok("Success buying " + quantity + " amount of duck! Happy Quacking!", getUserDucks(), getStoreDucks());
    }

    public DuckTransactionResponse sell(int quantity) {
        if (quantity <= 0) {
            return DuckTransactionResponse.error("Quantity must be greater than zero!", getUserDucks(), getStoreDucks());
        }

        int currentInventory;
        int nextInventory;

        do {
            currentInventory = userDucks.get();

            if (currentInventory < quantity) {
                return DuckTransactionResponse.error("You don't have enough ducks to sell!", getUserDucks(), getStoreDucks());
            }
            nextInventory = currentInventory - quantity;
        } while (!userDucks.compareAndSet(currentInventory, nextInventory));

        storeDucks.addAndGet(quantity);
        return DuckTransactionResponse.ok("Success selling " + quantity + " amount of duck! don't worry, they are in good hands!", getUserDucks(), getStoreDucks());
    }

    public int getUserDucks() {
        return userDucks.get();
    }

    public int getStoreDucks() {
        return storeDucks.get();
    }
}
