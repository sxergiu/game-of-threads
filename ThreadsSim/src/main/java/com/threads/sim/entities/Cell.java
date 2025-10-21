package com.threads.sim.entities;

import com.threads.sim.managers.ResourceManager;

import java.util.Random;

public abstract class Cell extends Thread {
    protected final int id;
    protected final int T_full;
    protected final int T_starve;
    protected int timesEaten = 0;
    protected boolean hungry = true;
    protected boolean alive = true;
    protected final ResourceManager resourceManager;
    protected final Random random = new Random();

    public Cell(int id, ResourceManager resourceManager, int T_full, int T_starve) {
        this.id = id;
        this.resourceManager = resourceManager;
        this.T_full = T_full;
        this.T_starve = T_starve;
    }

    @Override
    public void run() {
        try {
            while (alive) {
                if (hungry) {
                    if (resourceManager.consumeFood()) {
                        eat();
                    } else {
                        Thread.sleep(T_starve);
                        if (hungry) die();
                    }
                } else {
                    Thread.sleep(T_full);
                    hungry = true;
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected void eat() throws InterruptedException {
        timesEaten++;
        hungry = false;
        System.out.println("[" + getClass().getSimpleName() + " #" + id + "] Ate (total=" + timesEaten + ")");
        if (timesEaten >= 10) {
            reproduce();
        }
        Thread.sleep(200);
    }

    protected void die() {
        alive = false;
        resourceManager.removeCell(this);
        int foodReturned = random.nextInt(5) + 1;
        resourceManager.addFood(foodReturned);
        System.out.println("[" + getClass().getSimpleName() + " #" + id + "] Died (released " + foodReturned + " food)");
    }

    protected abstract void reproduce();
}
