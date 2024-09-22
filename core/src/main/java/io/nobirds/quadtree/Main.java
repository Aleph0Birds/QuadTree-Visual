package io.nobirds.quadtree;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.nobirds.quadtree.entity.Ball2D;
import io.nobirds.quadtree.entity.Entity;
import io.nobirds.quadtree.manager.EntityManager;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    Rectangle area = new Rectangle(200, 100, 200, 200);
    ShapeRenderer shapeRenderer;
    BitmapFont font;
    float curFps = 0;
    final float cdTimeSecond = 0.5f;
    float curCDTime = 0;

    SpriteBatch spriteBatch;
    FitViewport viewport;
    boolean viewportUpdated;

    Array<Entity> result;

    //InputManager inputManager;
    EntityManager entityManager;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_INFO);
        Gdx.graphics.setVSync(false);
        Gdx.graphics.setForegroundFPS(Integer.MAX_VALUE);
        //setScreen(new FirstScreen());
        viewport = new FitViewport(1440, 900);
        spriteBatch = new SpriteBatch();
        viewportUpdated = true;

        //inputManager = new InputManager();
        entityManager = new EntityManager();
        shapeRenderer = new ShapeRenderer();

        font = new BitmapFont();

        entityManager.init();
        //inputManager.init();

        createBalls(200);

        result = new Array<>();
        entityManager.entityTree.getItems(area, result);
        Gdx.app.log("Tag", "(" + result.size + ") " + result.toString());
    }

    public void createBalls(int count) {
        for(int i = 0; i < count; i++) {
            float x = (float)(Math.random() * 800);
            float y = (float)(Math.random() * 600);
            Ball2D ball = new Ball2D((float)(Math.random()*10+5), x, y);
            ball.velocity.set(new Vector2((float)(Math.random() * 200) - 50, (float)(Math.random() * 200) - 50));
            entityManager.addEntity(ball);
            entityManager.entityTree.add(ball);
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
        updateRect();
        draw();
        updateFPS();
    }

    Vector2 prevPos = new Vector2();
    private void updateRect() {
        Vector2 cursorPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(cursorPos);

        if (cursorPos.y == prevPos.y && cursorPos.x == prevPos.x) return;

        area.x = cursorPos.x - area.width / 2;
        area.y = cursorPos.y - area.height / 2;

        result.clear();
        entityManager.entityTree.getItems(area, result);

        prevPos.set(cursorPos);
    }

    private void updateFPS() {
        curCDTime += Gdx.graphics.getDeltaTime();
        if(curCDTime < cdTimeSecond) return;
        curFps = 1f / Math.max(0.0001f, Gdx.graphics.getDeltaTime());
        curCDTime = 0;
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

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(area.x, area.y, area.width, area.height);
        shapeRenderer.end();

        entityManager.draw(shapeRenderer);

        result.forEach(entity -> {
            float radius = ((Ball2D)entity).getRadius();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(entity.position.x - radius, entity.position.y - radius, radius * 2, radius * 2);
            shapeRenderer.end();
        });

        spriteBatch.begin();
        font.setColor(Color.GREEN);
        font.draw(spriteBatch, "FPS: " + curFps, 20, 880);
        spriteBatch.end();

    }
}
