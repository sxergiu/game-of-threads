package com.threads.sim.managers;

import com.threads.sim.entities.Cell;
import com.threads.sim.entities.SexualCell;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResourceManager {
    private AtomicInteger foodCount;
    private final Lock foodLock = new ReentrantLock();
    private final List<Cell> cells = new CopyOnWriteArrayList<>();
    private final Lock reproductionLock = new ReentrantLock();

    public ResourceManager(int initialFoodCount) {
        this.foodCount = new AtomicInteger(initialFoodCount);
    }

    public boolean consumeFood() {
        foodLock.lock();
        try {
            if (foodCount.get() > 0) {
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

    public List<Cell> getCells() {
        return cells;
    }

    public SexualCell findPartner(SexualCell requester) {
        reproductionLock.lock();
        try {
            for (Cell c : cells) {
                if (c instanceof SexualCell && c != requester && ((SexualCell) c).isReadyToMate()) {
                    return (SexualCell) c;
                }
            }
            return null;
        } finally {
            reproductionLock.unlock();
        }
    }
}
