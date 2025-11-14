package engine.core;

import java.util.Stack;

public class StateManager {
    private final Stack<GameState> states = new Stack<>();
    
    public void pushState(GameState state) {
        state.enter();
        states.push(state);
    }
    
    public void popState() {
        if (!states.isEmpty()) {
            states.pop().exit();
        }
    }
    
    public void update(float deltaTime) {
        if (!states.isEmpty()) {
            states.peek().update(deltaTime);
        }
    }
    
    public void render() {
        if (!states.isEmpty()) {
            states.peek().render();
        }
    }
}
