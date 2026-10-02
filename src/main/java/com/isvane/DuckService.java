package com.isvane;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class DuckService {

    private AtomicInteger ducks = new AtomicInteger(100);
    private AtomicInteger userDucks = new AtomicInteger(0);

    public String buy(int quantity) {
        int currentStock;
        int nextStock;

        do {
            currentStock = ducks.get();

            if (currentStock < quantity) {
                return "Not enough stock";
            }
            nextStock = currentStock - quantity;
        } while (!ducks.compareAndSet(currentStock, nextStock));

        userDucks.addAndGet(quantity);
        return "Success buying " + quantity + " amount of duck! Happy Quacking!";
    }

    public String sell(int quantity) {
        int currentInventory;
        int nextInventory;

        do {
            currentInventory = userDucks.get();

            if (currentInventory < quantity) {
                return "You don't have enough ducks to sell!";
            }
            nextInventory = currentInventory - quantity;
        } while (!userDucks.compareAndSet(currentInventory, nextInventory));

        ducks.addAndGet(quantity);
        return "Success selling " + quantity + " amount of duck! don't worry, they are in good hands!";
    }

    public int getUserDucks() {
        return userDucks.get();
    }

    public int getStoreDucks() {
        return ducks.get();
    }
}
