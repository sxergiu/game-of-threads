package com.threads.sim.managers;

import com.threads.sim.entities.Cell;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResourceManager {
    private AtomicInteger foodCount;
    private final Lock foodLock = new ReentrantLock();
    private final List<Cell> cells = new CopyOnWriteArrayList<>();

    public ResourceManager(int initialFoodCount) {
        this.foodCount = new AtomicInteger(initialFoodCount);
    }

    public boolean consumeFood() {
        // Logic here needs a lock despite using AtomicInteger
        // because there are 2 atomic operations in a sequence which doesn't guarantee thread safety
        foodLock.lock();
        try {
            int current = foodCount.get();
            if (current > 0) {
                foodCount.decrementAndGet();
                return true;
            }
            return false;
        } finally {
            foodLock.unlock();
        }
    }

    public void addFood(int amount) {
        foodCount.addAndGet(amount);
    }

    public int getFoodCount() {
        return foodCount.get();
    }

    public void addCell(Cell cell) {
        cells.add(cell);
    }

    public void removeCell(Cell cell) {
        cells.remove(cell);
    }
}