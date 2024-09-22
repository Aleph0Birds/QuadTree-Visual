package io.nobirds.quadtree.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.nobirds.quadtree.entity.Ball2D;
import io.nobirds.quadtree.entity.Entity;
import io.nobirds.quadtree.struct.QuadTree;

import java.util.ArrayList;

public class EntityManager {
    public QuadTree<Entity> entityTree;
    ArrayList<Entity> entities;
    ArrayList<Entity> newEntities;
    ArrayList<Entity> removeEntities;

    public void init() {
        entities = new ArrayList<>();
        newEntities = new ArrayList<>();
        removeEntities = new ArrayList<>();
        entityTree = new QuadTree<>(4, new Rectangle(0, 0, 800, 600));
    }

    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        for(Entity entity : entities) {
            entity.update(deltaTime);
        }

        doCollide();


        if (!newEntities.isEmpty()) {
            entities.addAll(newEntities);
            newEntities.clear();
        }

        if (!removeEntities.isEmpty()) {
            entities.removeAll(removeEntities);
            removeEntities.clear();
        }
    }

    private void doCollide() {
        int listSize = entities.size();
        for(int i = 0; i < listSize; i++) {
            Ball2D entity = (Ball2D) entities.get(i);
            for(int j = i + 1; j < listSize; j++) {
                //if (j == i) continue;
                Ball2D other = (Ball2D)entities.get(j);
                if(entity.position.dst(other.position) <= entity.getRadius() + other.getRadius()) {
                    entity.setColliding(true);
                    other.setColliding(true);
                    clampBalls(entity, other);
                    updateVelocity(entity, other);
                }
            }
        }
    }

    private void clampBalls(Ball2D ball, Ball2D other) {
        Vector2 distVec = ball.position.cpy().sub(other.position);
        float dist = distVec.len();
        float overlap = (ball.getRadius() + other.getRadius()) - dist;
        Vector2 correction = distVec.scl(overlap / dist / 2);
        ball.position.add(correction);
        other.position.sub(correction);
    }

    private void updateVelocity(Ball2D ball, Ball2D other) {
        // change the velocity of the balls using the formula for 2d elastic collision

        Vector2 normal = ball.position.cpy().sub(other.position).nor();

        float dpTan1 = ball.velocity.x * -normal.y + ball.velocity.y * normal.x;
        float dpTan2 = other.velocity.x * -normal.y + other.velocity.y * normal.x;
        float dpNorm1 = ball.velocity.x * normal.x + ball.velocity.y * normal.y;
        float dpNorm2 = other.velocity.x * normal.x + other.velocity.y * normal.y;
        float m1 = ball.getRadius();
        float m2 = other.getRadius();
        float m1m2 = m1 + m2;
        float dpNorm1After = (dpNorm1 * (m1 - m2) + 2 * m2 * dpNorm2) / m1m2;
        float dpNorm2After = (dpNorm2 * (m2 - m1) + 2 * m1 * dpNorm1) / m1m2;
        ball.velocity.set(dpNorm1After * normal.x + dpTan1 * -normal.y, dpNorm1After * normal.y + dpTan1 * normal.x);
        other.velocity.set(dpNorm2After * normal.x + dpTan2 * -normal.y, dpNorm2After * normal.y + dpTan2 * normal.x);
    }

    public void draw(ShapeRenderer renderer) {
        renderer.setColor(Color.WHITE);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        for(Entity entity : entities) {
            entity.draw(renderer);
        }
        renderer.end();

//        renderer.setColor(Color.GOLD);
//        renderer.begin(ShapeRenderer.ShapeType.Line);
//        entityTree.draw(renderer);
//        renderer.end();
    }

    public void addEntity(Entity entity) {
        newEntities.add(entity);
    }

    public void removeEntity(Entity entity) {
        removeEntities.add(entity);
    }
}
