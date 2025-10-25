package com.threads.sim.managers;

import com.threads.sim.entities.AsexualCell;
import com.threads.sim.entities.Cell;
import com.threads.sim.entities.SexualCell;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ResourceManager {
    private final AtomicInteger foodCount;
    private final List<Cell> cells = new CopyOnWriteArrayList<>();
    private final Lock reproductionLock = new ReentrantLock();
    private final Object foodMonitor = new Object();

    // Statistics
    private final AtomicInteger totalBirths = new AtomicInteger(0);
    private final AtomicInteger totalDeaths = new AtomicInteger(0);
    private final AtomicInteger totalFoodConsumed = new AtomicInteger(0);

    public ResourceManager(int initialFoodCount) {
        this.foodCount = new AtomicInteger(initialFoodCount);
    }

    public boolean consumeFood() {
        if (foodCount.get() > 0) {
            int current = foodCount.getAndDecrement();
            if (current > 0) {
                totalFoodConsumed.incrementAndGet();
                return true;
            } else {
                foodCount.incrementAndGet();
                return false;
            }
        }
        return false;
    }

    public void addFood(int amount) {
        foodCount.addAndGet(amount);
        synchronized (foodMonitor) {
            foodMonitor.notifyAll();
        }
    }

    public int getFoodCount() {
        return foodCount.get();
    }

    public Object getFoodMonitor() {
        return foodMonitor;
    }

    public void addCell(Cell cell) {
        cells.add(cell);
        totalBirths.incrementAndGet();
    }

    public void removeCell(Cell cell) {
        cells.remove(cell);
        totalDeaths.incrementAndGet();
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
    public int getTotalBirths() {
        return totalBirths.get();
    }

    public int getTotalDeaths() {
        return totalDeaths.get();
    }

    public int getTotalFoodConsumed() {
        return totalFoodConsumed.get();
    }

    public int getAsexualCellCount() {
        return (int) cells.stream().filter(c -> c instanceof AsexualCell).count();
    }

    public int getSexualCellCount() {
        return (int) cells.stream().filter(c -> c instanceof SexualCell).count();
    }

    public String getStatistics() {
        return String.format(
                "[Statistics] Food: %d | Living cells: %d (Asexual: %d, Sexual: %d) | Total births: %d | Total deaths: %d | Food consumed: %d",
                getFoodCount(), getCells().size(), getAsexualCellCount(), getSexualCellCount(),
                getTotalBirths(), getTotalDeaths(), getTotalFoodConsumed()
        );
    }
}
