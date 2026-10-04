package com.isvane;

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

    public String buy(int quantity) {
        if (quantity <= 0) {
            return "Quantity must be greater than zero!";
        }

        int currentStock;
        int nextStock;

        do {
            currentStock = storeDucks.get();

            if (currentStock < quantity) {
                return "Not enough stock";
            }
            nextStock = currentStock - quantity;
        } while (!storeDucks.compareAndSet(currentStock, nextStock));

        userDucks.addAndGet(quantity);
        return "Success buying " + quantity + " amount of duck! Happy Quacking!";
    }

    public String sell(int quantity) {
        if (quantity <= 0) {
            return "Quantity must be greater than zero!";
        }

        int currentInventory;
        int nextInventory;

        do {
            currentInventory = userDucks.get();

            if (currentInventory < quantity) {
                return "You don't have enough ducks to sell!";
            }
            nextInventory = currentInventory - quantity;
        } while (!userDucks.compareAndSet(currentInventory, nextInventory));

        storeDucks.addAndGet(quantity);
        return "Success selling " + quantity + " amount of duck! don't worry, they are in good hands!";
    }

    public int getUserDucks() {
        return userDucks.get();
    }

    public int getStoreDucks() {
        return storeDucks.get();
    }
}
