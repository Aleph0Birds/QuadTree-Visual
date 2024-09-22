package io.nobirds.quadtree.manager;

import io.nobirds.quadtree.entity.Entity;

import java.util.ArrayList;

public class EntityManager {
    ArrayList<Entity> entities;
    ArrayList<Entity> newEntities;
    ArrayList<Entity> removeEntities;

    public void init() {
        entities = new ArrayList<>();
        newEntities = new ArrayList<>();
        removeEntities = new ArrayList<>();
    }

    public void update() {
        for(Entity entity : entities) {
            entity.update();
        }

        if (!newEntities.isEmpty()) {
            entities.addAll(newEntities);
            newEntities.clear();
        }

        if (!removeEntities.isEmpty()) {
            entities.removeAll(removeEntities);
            removeEntities.clear();
        }
    }

    public void addEntity(Entity entity) {
        newEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        removeEntities.add(entity);
    }
}
