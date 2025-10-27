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
        synchronized (this) {
            readyToMate = true;
        }
        System.out.println("[SexualCell #" + id + "] Ready to mate");

        //while looking for a partner, another cell might found it and mated with it
        //in that case, exit it's own search
        for (int i = 0; i < 10; i++) { // retry up to 10 times
            synchronized (this) {
                if (!readyToMate) {
                    System.out.println("[SexualCell #" + id + "] Already mated as partner");
                    return;
                }
            }
            
            //finding potential partner
            SexualCell partner = resourceManager.findPartner(this);
            if (partner != null && partner != this) {
                //both cells must be locked to prevent other cells from mating with them
                Object firstLock = id < partner.id ? this : partner;
                Object secondLock = id < partner.id ? partner : this;

                synchronized (firstLock) {
                    synchronized (secondLock) {
                        if (!partner.isReadyToMate() || !this.readyToMate) {
                            continue;
                        }

                        readyToMate = false;
                        partner.readyToMate = false;

                        SexualCell baby = new SexualCell((int) (Math.random() * 10000),
                                resourceManager, T_full, T_starve);
                        resourceManager.addCell(baby);
                        baby.start();

                        System.out.println("[SexualCell #" + id + "] Mated with #" + partner.id + " → 1 new cell (#" + baby.id + ")");
                        return;
                    }
                }
            }
            synchronized (this) {
                try {
                    this.wait(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        System.out.println("[SexualCell #" + id + "] Could not find mate after retries");
        synchronized (this) {
            readyToMate = false;
        }
    }
}
