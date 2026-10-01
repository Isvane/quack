package com.isvane;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class DuckService {

    private AtomicInteger ducks = new AtomicInteger(100);

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

        return "Success buying " + quantity + " amount of duck! Happy Quacking!";
    }
}
