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
import io.nobirds.quadtree.io.Keybind;
import io.nobirds.quadtree.manager.EntityManager;
import io.nobirds.quadtree.manager.InputManager;
import io.nobirds.quadtree.struct.RectArea;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    RectArea area = new RectArea(new Rectangle(200, 100, 200, 200));
    ShapeRenderer shapeRenderer;
    BitmapFont font;
    float curFps = 0;
    final float cdTimeSecond = 0.5f;
    float curCDTime = 0;
    boolean drawDebug = false;
    boolean pausing = false;

    SpriteBatch spriteBatch;
    FitViewport viewport;
    boolean viewportUpdated;

    Array<Entity> result;

    InputManager inputManager;
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

        inputManager = new InputManager();
        entityManager = new EntityManager();
        shapeRenderer = new ShapeRenderer();

        font = new BitmapFont();

        entityManager.init();
        inputManager.init();

        Keybind.bindDefaultKeys();
        registerKeyListeners();
        createBalls(100);

        result = new Array<>();
        entityManager.entityTree.getItems(area, result);
        //Gdx.app.log("Tag", "(" + result.size + ") " + result.toString());
    }

    private void registerKeyListeners() {
        inputManager.addKeyListener(Keybind.DEBUG, () ->
            drawDebug = !drawDebug
        ).addKeyListener(Keybind.PAUSE, () -> {
            if(!pausing)
                pause();
            else
                resume();
            pausing = !pausing;
        }).addMouseListener(Keybind.MOUSE_LCLICK, () -> {
            float randX = (float)(Math.random());
            float randY = (float)(Math.random());
            Gdx.app.log("Input", "Click at (" + Gdx.input.getX() + ", " + Gdx.graphics.getHeight() + " - " + Gdx.input.getY() + ")");
            entityManager.addEntity(new Ball2D(5, Gdx.input.getX() + randX, Gdx.graphics.getHeight() - Gdx.input.getY() - randY));
        });

    }

    public void createBalls(int count) {
        for(int i = 0; i < count; i++) {
            float x = (float)(Math.random() * 700);
            float y = (float)(Math.random() * 500);
            Ball2D ball = new Ball2D((float)(Math.random() * 1 + 10), x, y);
            ball.velocity.set(new Vector2((float)(Math.random() * 150) - 75, (float)(Math.random() * 150) - 75));
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
        inputManager.update();
        if(!pausing) {
            entityManager.update();
            updateRect();
        }
        draw();
        updateFPS();
    }

    Vector2 prevPos = new Vector2();
    private void updateRect() {
        Vector2 cursorPos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(cursorPos);

        if (cursorPos.y == prevPos.y && cursorPos.x == prevPos.x) return;

        area.rect.x = cursorPos.x - area.rect.width / 2;
        area.rect.y = cursorPos.y - area.rect.height / 2;

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

    private void debugDraw() {
        if(!drawDebug) return;
        result.forEach(entity -> {
            float radius = ((Ball2D)entity).getRadius();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.GREEN);
            shapeRenderer.rect(entity.position.x - radius, entity.position.y - radius, radius * 2, radius * 2);
            shapeRenderer.end();
        });

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GREEN);
        shapeRenderer.rect(area.rect.x, area.rect.y, area.rect.width, area.rect.height);
        shapeRenderer.end();

        spriteBatch.begin();
        font.setColor(Color.GREEN);
        font.draw(spriteBatch, "FPS: " + curFps, 20, 880);
        spriteBatch.end();

        entityManager.drawDebug(shapeRenderer);
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        if(viewportUpdated) {
            viewportUpdated = false;
            viewport.apply();
            spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
            shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        }


        entityManager.draw(shapeRenderer);

        debugDraw();
    }
}
