package com.threads.sim.entities;

import com.threads.sim.managers.ResourceManager;

public class SexualCell extends Cell {
    private boolean readyToMate = false;

    public SexualCell(int id, ResourceManager resourceManager, int T_full, int T_starve) {
        super(id, resourceManager, T_full, T_starve);
    }

    public boolean isReadyToMate() {
        return readyToMate;
    }

    @Override
    protected void reproduce() {
        readyToMate = true;
        System.out.println("[SexualCell #" + id + "] Ready to mate");

        for (int i = 0; i < 10; i++) { // retry up to 10 times
            SexualCell partner = resourceManager.findPartner(this);
            if (partner != null && partner != this) {
                synchronized (partner) {
                    readyToMate = false;
                    partner.readyToMate = false;
                    timesEaten = 0;
                    partner.timesEaten = 0;

                    SexualCell baby = new SexualCell((int) (Math.random() * 10000),
                            resourceManager, T_full, T_starve);
                    resourceManager.addCell(baby);
                    baby.start();

                    System.out.println("[SexualCell #" + id + "] Mated with #" + partner.id + " → 1 new cell");
                    return;
                }
            }
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("[SexualCell #" + id + "] Could not find mate after retries");
        readyToMate = false;
    }
}
