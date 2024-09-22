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
        checkCollision(velocity);
    }

    public void moveDelta(Vector2 velocity, float deltaTime) {
        position.mulAdd(velocity, deltaTime);
        checkCollision(velocity);
    }

    private void checkCollision(Vector2 velocity) {
        if (hitBoundX()) {
            velocity.x = -velocity.x;
        }

        if (hitBoundY()) {
            velocity.y = -velocity.y;
        }
    }

    public boolean hitBoundX() {
        return position.x < 0 || position.x > 1440;
    }

    public boolean hitBoundY() {
        return position.y < 0 || position.y > 900;
    }
}
