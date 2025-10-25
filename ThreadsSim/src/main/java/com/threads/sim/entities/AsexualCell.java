package com.threads.sim.entities;

import com.threads.sim.managers.ResourceManager;

public class AsexualCell extends Cell {
    public AsexualCell(int id, ResourceManager resourceManager, int T_full, int T_starve) {
        super(id, resourceManager, T_full, T_starve);
    }

    @Override
    protected void reproduce() {
        AsexualCell child1 = new AsexualCell((int) (Math.random() * 10000),
                resourceManager, T_full, T_starve);
        AsexualCell child2 = new AsexualCell((int) (Math.random() * 10000),
                resourceManager, T_full, T_starve);

        System.out.println("[AsexualCell #" + id + "] Reproduced → 2 new cells (#" + child1.id + ", #" + child2.id + ")");

        resourceManager.addCell(child1);
        child1.start();
        resourceManager.addCell(child2);
        child2.start();
    }
}
