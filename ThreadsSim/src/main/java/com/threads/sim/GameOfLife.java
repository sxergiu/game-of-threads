package com.threads.sim;

import com.threads.sim.entities.*;
import com.threads.sim.managers.ResourceManager;

import java.util.concurrent.TimeUnit;

public class GameOfLife {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Starting Game of Life Simulation ===");

        ResourceManager manager = new ResourceManager(200); // increased food

        // Create initial population
        for (int i = 0; i < 3; i++) {
            AsexualCell a = new AsexualCell(i, manager, 1000, 3000);
            manager.addCell(a);
            a.start();
        }
        for (int i = 3; i < 6; i++) {
            SexualCell s = new SexualCell(i, manager, 1000, 3000);
            manager.addCell(s);
            s.start();
        }

        // Monitor
        while (true) {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("[Stats] Food: " + manager.getFoodCount()
                    + " | Living cells: " + manager.getCells().size());

            if (manager.getCells().isEmpty()) {
                System.out.println("All cells have died. Simulation ended.");
                break;
            }

            // Safety stop condition to prevent infinite population explosion
            if (manager.getCells().size() > 100) {
                System.out.println("Population exceeded 100 cells. Stopping simulation.");
                System.exit(0);
            }
        }
    }
}
