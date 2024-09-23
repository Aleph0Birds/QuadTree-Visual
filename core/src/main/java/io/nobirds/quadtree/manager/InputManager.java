package io.nobirds.quadtree.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import io.nobirds.quadtree.io.KeyListener;
import io.nobirds.quadtree.io.Keybind;

import java.util.Arrays;
import java.util.HashMap;

public class InputManager {
    public static final int DEFAULT_MAX_KEYS = 256;

    static InputManager instance;
    private final Input input = Gdx.input;
    private final KeyListener[] keyListeners = new KeyListener[DEFAULT_MAX_KEYS];
    private final HashMap<Integer, KeyListener[]> keyListenerMap = new HashMap<>();

    public void init() {
        instance = this;
    }

    public void update() {
        for (var entry : keyListenerMap.entrySet()) {
            int keyCode = entry.getKey();

            if (input.isKeyJustPressed(keyCode)) {
                KeyListener[] listeners = entry.getValue();
                for (KeyListener listener : listeners) {
                    if (listener != null)
                        listener.keyDown();
                }
            }
        }
    }

    public InputManager addKeyListener(String key, KeyListener listener) {
        Integer[] keyCodes = Keybind.keybinds.get(key);
        if (keyCodes == null) {
            Gdx.app.error("InputManager", "Keybind " + key + " not found.");
            return this;
        }

        for (Integer keyCode : keyCodes) {
            if (keyCode == null)
                break;
            KeyListener[] listeners = keyListenerMap.get(keyCode);
            if (listeners == null)
                listeners = new KeyListener[Keybind.MAX_KEYBINDINGS];

            int i = 0;
            for (; i < Keybind.MAX_KEYBINDINGS; i++) {
                if (listeners[i] == null) {
                    listeners[i] = listener;
                    break;
                }
            }

            if (i >= Keybind.MAX_KEYBINDINGS) {
                Gdx.app.error("InputManager", "Keybind " + key + " has reached the maximum number of keybindings.");
                return this;
            }

            keyListenerMap.put(keyCode, listeners);

        }

        return this;
    }

    public static InputManager getInstance() {
        assert instance != null;
        return instance;
    }
}
