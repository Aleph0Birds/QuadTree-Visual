package io.nobirds.quadtree.struct;

import com.badlogic.gdx.math.Rectangle;

public class RectArea extends Area {

    public Rectangle rect;

    public RectArea(float x, float y, float width, float height) {
        super(x, y);
        rect = new Rectangle(x, y, width, height);
    }

    public RectArea(Rectangle rect) {
        super(rect.x, rect.y);
        this.rect = rect;
    }

    @Override
    public boolean contains(float x, float y) {
        return rect.contains(x, y);
    }

    @Override
    public boolean overlaps(Rectangle area) {
        return rect.overlaps(area);
    }

    @Override
    public float getArea() {
        return rect.area();
    }

    @Override
    public Area copy() {
        return new RectArea(x, y, rect.width, rect.height);
    }
}
