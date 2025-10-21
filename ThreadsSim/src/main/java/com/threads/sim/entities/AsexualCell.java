package com.threads.sim.entities;

import com.threads.sim.managers.ResourceManager;

public class AsexualCell extends Cell {
    public AsexualCell(int id, ResourceManager resourceManager, int T_full, int T_starve) {
        super(id, resourceManager, T_full, T_starve);
    }

    @Override
    protected void reproduce() {
        timesEaten = 0;
        System.out.println("[AsexualCell #" + id + "] Reproduced → 2 new cells");

        for (int i = 0; i < 2; i++) {
            AsexualCell child = new AsexualCell((int) (Math.random() * 10000),
                    resourceManager, T_full, T_starve);
            resourceManager.addCell(child);
            child.start();
        }
    }
}
