package com.isvane;

import java.util.concurrent.atomic.AtomicInteger;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GreetingService {

    private final AtomicInteger quack = new AtomicInteger(0);

    public String greeting(String name) {
        int count = quack.incrementAndGet();
        return "\nQuack " + name + "\n\nQuack time = " + count;
    }
}
