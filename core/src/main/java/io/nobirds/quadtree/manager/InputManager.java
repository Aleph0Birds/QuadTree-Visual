package io.nobirds.quadtree.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputManager {
    public static final int DEFAULT_MAX_KEYS = 256;

    static InputManager instance;
    private final Input input = Gdx.input;



    public void init() {
        instance = this;
    }

    public void update() {

    }

    public static InputManager getInstance() {
        assert instance != null;
        return instance;
    }
}
