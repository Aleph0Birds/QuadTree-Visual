package io.nobirds.quadtree.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Ball2D extends Entity implements ShapeD {
    private float radius;

    public Ball2D(float radius, Vector2 position) {
        this.radius = radius;
        this.position = position;
    }

    public Ball2D(float radius) {
        this(radius, Vector2.Zero);
    }

    public Ball2D(float radius, float x, float y) {
        this(radius, new Vector2(x, y));
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;

    }

    @Override
    public void update() {
        move(this.velocity);
    }

    public void draw(ShapeRenderer renderer) {
        renderer.circle(position.x, position.y, radius);
    }
}
