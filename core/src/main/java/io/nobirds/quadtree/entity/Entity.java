package io.nobirds.quadtree.entity;

import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Vector2 position;
    protected Vector2 velocity;

    protected void setPosition(Vector2 position) {
        this.position = position;
    };

    protected Vector2 getPosition() {
        return position;
    }

    protected void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    };

    protected Vector2 getVelocity() {
        return velocity;
    }

    public abstract void update();

    protected void move(Vector2 velocity) {
        position = position.add(velocity);
    }
}
