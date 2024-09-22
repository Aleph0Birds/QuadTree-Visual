package io.nobirds.quadtree.struct;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class CircleArea extends Area {

    private final float radius;
    private final float radiusSqr;

    public CircleArea(float x, float y, float radius) {
        super(x, y);
        this.radius = radius;
        this.radiusSqr = radius * radius;
    }

    @Override
    public boolean contains(float x, float y) {
        return Math.sqrt(Math.pow(x - this.x, 2) + Math.pow(y - this.y, 2)) <= radius;
    }

    @Override
    public boolean overlaps(Rectangle area) {
        float closestX = Math.max(area.x, Math.min(this.x, area.x + area.width));
        float closestY = Math.max(area.y, Math.min(this.y, area.y + area.height));

        return Math.pow(closestX - this.x, 2) + Math.pow(closestY - this.y, 2) <= radiusSqr;
    }

    @Override
    public float getArea() {
        return (float)(Math.PI * Math.pow(radius, 2));
    }

    @Override
    public Area copy() {
        return new CircleArea(x, y, radius);
    }
}
