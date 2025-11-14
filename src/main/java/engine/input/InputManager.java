package engine.input;

import java.util.HashMap;
import java.util.Map;

public class InputManager {
    private final Map<Integer, Boolean> keys = new HashMap<>();
    private final Map<Integer, Boolean> previousKeys = new HashMap<>();
    private final Map<Integer, Boolean> mouseButtons = new HashMap<>();
    private double mouseX, mouseY;
    
    public void init() {
        System.out.println("InputManager initialized");
    }
    
    public void update() {
        // Copy current state to previous state
        previousKeys.clear();
        previousKeys.putAll(keys);
    }
    
    public void setKeyPressed(int key, boolean pressed) {
        keys.put(key, pressed);
    }
    
    public boolean isKeyPressed(int key) {
        return keys.getOrDefault(key, false);
    }
    
    public boolean isKeyJustPressed(int key) {
        return keys.getOrDefault(key, false) && !previousKeys.getOrDefault(key, false);
    }
    
    public boolean isMouseButtonPressed(int button) {
        return mouseButtons.getOrDefault(button, false);
    }
    
    public double getMouseX() { return mouseX; }
    public double getMouseY() { return mouseY; }
}
