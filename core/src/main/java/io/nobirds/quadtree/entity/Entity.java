package io.nobirds.quadtree.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    public final Vector2 position = new Vector2();
    public final Vector2 velocity = new Vector2();

    public abstract void update(float deltaTime);

    public abstract void draw(ShapeRenderer renderer);

    public void move(Vector2 velocity) {
        position.add(velocity);
    }

    public void moveDelta(Vector2 velocity, float deltaTime) {
        position.mulAdd(velocity, deltaTime);
    }
}
