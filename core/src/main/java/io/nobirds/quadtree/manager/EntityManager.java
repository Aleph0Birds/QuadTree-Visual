package io.nobirds.quadtree.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
        float deltaTime = Gdx.graphics.getDeltaTime();
        for(Entity entity : entities) {
            entity.update(deltaTime);
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

    public void draw(ShapeRenderer renderer) {
        for(Entity entity : entities) {
            entity.draw(renderer);
        }
    }

    public void addEntity(Entity entity) {
        newEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        removeEntities.add(entity);
    }
}
