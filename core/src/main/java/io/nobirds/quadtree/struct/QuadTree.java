package io.nobirds.quadtree.struct;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import io.nobirds.quadtree.entity.Entity;

public class QuadTree<T extends Entity> {

    Rectangle bounds;
    int capacity;
    T[] items;
    int count = 0;
    boolean divided = false;

    public QuadTree<T> northWest, northEast, southWest, southEast;

    @SuppressWarnings("unchecked")
    public QuadTree(int capacity, Rectangle bounds) {
        this.bounds = bounds;
        this.capacity = capacity;
        items = (T[])(java.lang.reflect.Array.newInstance(Entity.class, capacity));
    }

    public boolean add(T entity) {
        //if (entity == null) return;
        if (!bounds.contains(entity.position)) return false;

        if (count < capacity) {
            items[count] = (entity);
            count++;
            return true;
        } else {
            if(!divided)
                this.subdivide();
            if(northEast.add(entity))
                return true;
            if(northWest.add(entity))
                return true;
            if(southEast.add(entity))
                return true;
            return southWest.add(entity);
        }
    }

    public void getItems(Area range, Array<T> result) {

        if (!range.overlaps(bounds)) return;

        for (short i = 0; i < count; i++) {
            if (range.contains(items[i].position)) {
                result.add(items[i]);
            }
        }

        if (divided) {
            northWest.getItems(range ,result);
            northEast.getItems(range, result);
            southWest.getItems(range, result);
            southEast.getItems(range, result);
        }
    }

    public void subdivide() {
        float x = bounds.x;
        float y = bounds.y;
        float w = bounds.width / 2;
        float h = bounds.height / 2;

        northWest = new QuadTree<>(capacity, new Rectangle(x, y + h, w, h));
        northEast = new QuadTree<>(capacity, new Rectangle(x + w, y + h, w, h));
        southWest = new QuadTree<>(capacity, new Rectangle(x, y, w, h));
        southEast = new QuadTree<>(capacity, new Rectangle(x + w, y, w, h));
        divided = true;
    }

    // visualization
    public void draw(ShapeRenderer renderer) {


        renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        if (divided) {
            northWest.draw(renderer);
            northEast.draw(renderer);
            southWest.draw(renderer);
            southEast.draw(renderer);
        }
    }
}
