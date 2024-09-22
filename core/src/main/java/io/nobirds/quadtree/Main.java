package io.nobirds.quadtree;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.nobirds.quadtree.entity.Ball2D;
import io.nobirds.quadtree.manager.EntityManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    Ball2D ball2D;
    ShapeRenderer shapeRenderer;

    SpriteBatch spriteBatch;
    FitViewport viewport;
    boolean viewportUpdated;

    InputManager inputManager;
    EntityManager entityManager;

    @Override
    public void create() {
        //setScreen(new FirstScreen());
        viewport = new FitViewport(800, 600);
        spriteBatch = new SpriteBatch();
        viewportUpdated = true;

        inputManager = new InputManager();
        entityManager = new EntityManager();

        ball2D = new Ball2D(10, 400, 400);
        shapeRenderer = new ShapeRenderer();

        entityManager.init();
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
        ball2D.draw(shapeRenderer);
        shapeRenderer.end();

        spriteBatch.begin();
        spriteBatch.end();
    }
}
