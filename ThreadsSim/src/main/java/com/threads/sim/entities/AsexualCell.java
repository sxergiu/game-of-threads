package com.threads.sim.entities;

import com.threads.sim.managers.ResourceManager;

public class AsexualCell extends Cell {
    public AsexualCell(int id, ResourceManager resourceManager, int T_full, int T_starve) {
        super(id, resourceManager, T_full, T_starve);
    }
}