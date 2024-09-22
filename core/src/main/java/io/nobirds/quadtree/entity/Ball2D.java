package io.nobirds.quadtree.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Ball2D extends Entity implements ShapeD {
    private float radius;

    public Ball2D(float radius, Vector2 position) {
        this.radius = radius;
        this.position.set(position);
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
    public void update(float deltaTime) {
        moveDelta(this.velocity, deltaTime);
        clampPosition();
    }

    public void draw(ShapeRenderer renderer) {
        renderer.circle(position.x, position.y, radius, 16);
    }

    @Override
    public boolean hitBoundX() {
        return position.x <= radius || position.x >= 800 - radius;
    }

    @Override
    public boolean hitBoundY() {
        return position.y <= radius || position.y >= 600 - radius;
    }

    public void clampPosition() {
        if (hitBoundX())
            position.x = MathUtils.clamp(position.x, radius, 800 - radius);

        if (hitBoundY())
            position.y = MathUtils.clamp(position.y, radius, 600 - radius);
    }
}
