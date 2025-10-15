package com.threads.sim.entities;

import com.threads.sim.managers.ResourceManager;

public abstract class Cell extends Thread {
    protected final int id;
    protected final int T_full;
    protected final int T_starve;
    protected int timesEaten;
    protected boolean hungry;
    protected boolean alive;
    protected ResourceManager resourceManager;

    public Cell(int id, ResourceManager resourceManager, int T_full, int T_starve) {
        this.id = id;
        this.resourceManager = resourceManager;
        this.T_full = T_full;
        this.T_starve = T_starve;
    }
}