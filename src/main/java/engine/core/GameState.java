package engine.core;

public interface GameState {
    void enter();
    void update(float deltaTime);
    void render();
    void exit();
}
