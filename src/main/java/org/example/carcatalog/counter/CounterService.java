package org.example.carcatalog.counter;

import lombok.Data;

import java.util.Objects;

@Data
public class CounterService {
    private static CounterService counterService;

    private int counter;

    public static synchronized CounterService getCounterService() {
        if (Objects.isNull(counterService)) {
            counterService = new CounterService();
            counterService.counter = 0;
        }
        return counterService;
    }

    public synchronized void incrementCounter() {
        counter++;
    }

    public synchronized int getCounter() {
        return counter;
    }
}
