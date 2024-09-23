package io.nobirds.quadtree.io;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.HashMap;

public class Keybind {
    public static final byte MAX_KEYBINDINGS = 4;
    public static final HashMap<String, Integer[]> keybinds = new HashMap<>();

    public static final String DEBUG = "debug";
    public static final String PAUSE = "pause";
    public static final String MOUSE_LCLICK = "click";

    public static void bindDefaultKeys() {
        bindKey(DEBUG, Input.Keys.F3);
        bindKey(PAUSE, Input.Keys.P);
        bindKey(MOUSE_LCLICK, Input.Buttons.LEFT);
    }

    public static void bindKey(String keyName, int keyCode) {
        Integer[] keys = keybinds.get(keyName);
        if (keys == null)
            keys = new Integer[MAX_KEYBINDINGS];

        int i = 0;
        for (; i < MAX_KEYBINDINGS; i++) {
            if (keys[i] == null) {
                keys[i] = keyCode;
                break;
            }
        }

        if (i >= MAX_KEYBINDINGS) {
            Gdx.app.error("Keybind", "Keybind " + keyName + " has reached the maximum number of keybindings.");
            return;
        }


        keybinds.put(keyName, keys);
    }
}
