package io.nobirds.quadtree.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Ball2D extends Entity implements ShapeD {
    private float radius;
    private boolean colliding = false;

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

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    @Override
    public void update(float deltaTime) {
        moveDelta(this.velocity, deltaTime);
        clampPosition();
        if(colliding) colliding = false;
    }

    public void draw(ShapeRenderer renderer) {
        if (colliding)
            renderer.setColor(Color.RED);

        renderer.circle(position.x, position.y, radius, 16);

        if (colliding)
            renderer.setColor(Color.WHITE);
    }

    @Override
    public boolean hitBoundX() {
        return position.x <= radius || position.x >= 1440 - radius;
    }

    @Override
    public boolean hitBoundY() {
        return position.y <= radius || position.y >= 900 - radius;
    }

    public void clampPosition() {
        if (hitBoundX())
            position.x = MathUtils.clamp(position.x, radius, 1440 - radius);

        if (hitBoundY())
            position.y = MathUtils.clamp(position.y, radius, 900 - radius);
    }
}
