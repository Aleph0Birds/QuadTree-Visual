package io.nobirds.quadtree.struct;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Area {

    public Area(float x, float y) {
        this.x = x;
        this.y = y;
    }

    protected float x, y;

    public abstract boolean contains(float x, float y);

    public boolean contains(Vector2 position) {
        return contains(position.x, position.y);
    }

    public abstract boolean overlaps(Rectangle area);

    public float getX() { return x; };

    public float getY() { return y; };

    public abstract float getArea();

    public abstract Area copy();

}
