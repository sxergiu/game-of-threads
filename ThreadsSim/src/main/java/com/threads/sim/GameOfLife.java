package com.threads.sim;

import com.threads.sim.entities.*;
import com.threads.sim.managers.ResourceManager;

import java.util.concurrent.TimeUnit;

public class GameOfLife {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Starting Game of Life Simulation ===");

        ResourceManager manager = new ResourceManager(200);

        // Create initial population
        for (int i = 0; i < 1; i++) {
            AsexualCell a = new AsexualCell(i, manager, 1000, 3000);
            manager.addCell(a);
            a.start();
        }
        for (int i = 1; i < 6; i++) {
            SexualCell s = new SexualCell(i, manager, 1000, 3000);
            manager.addCell(s);
            s.start();
        }

        while (true) {
            TimeUnit.SECONDS.sleep(5);
            System.out.println(manager.getStatistics());

            if (manager.getCells().isEmpty()) {
                System.out.println("\n=== SIMULATION ENDED ===");
                System.out.println("All cells have died.");
                System.out.println(manager.getStatistics());
                break;
            }

            if (manager.getCells().size() > 100) {
                System.out.println("\n=== SIMULATION ENDED ===");
                System.out.println("Population exceeded 100 cells. Stopping simulation.");
                System.out.println(manager.getStatistics());
                System.exit(0);
            }
        }
    }
}
