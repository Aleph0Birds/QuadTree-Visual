package io.nobirds.quadtree;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.nobirds.quadtree.entity.Ball2D;
import io.nobirds.quadtree.manager.EntityManager;
import io.nobirds.quadtree.manager.InputManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    Ball2D[] balls;
    ShapeRenderer shapeRenderer;

    SpriteBatch spriteBatch;
    FitViewport viewport;
    boolean viewportUpdated;

    //InputManager inputManager;
    EntityManager entityManager;

    @Override
    public void create() {
        //setScreen(new FirstScreen());
        viewport = new FitViewport(800, 600);
        spriteBatch = new SpriteBatch();
        viewportUpdated = true;

        //inputManager = new InputManager();
        entityManager = new EntityManager();

        shapeRenderer = new ShapeRenderer();

        entityManager.init();
        //inputManager.init();

        createBalls(10);
    }

    public void createBalls(int count) {
        for(int i = 0; i < count; i++) {
            float x = (float)(Math.random() * 800);
            float y = (float)(Math.random() * 600);
            Ball2D ball = new Ball2D((float)(Math.random()*20), x, y);
            ball.velocity.set(new Vector2((float)(Math.random() * 100) - 50, (float)(Math.random() * 100) - 50));
            entityManager.addEntity(ball);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        viewportUpdated = true;
    }

    @Override
    public void render() {
        updateManagers();
        draw();
    }

    private void updateManagers() {
        entityManager.update();
        //inputManager.update();
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        if(viewportUpdated) {
            viewportUpdated = false;
            viewport.apply();
            spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
            shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        entityManager.draw(shapeRenderer);
        shapeRenderer.end();

        spriteBatch.begin();
        spriteBatch.end();
    }
}
