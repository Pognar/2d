package game.systems;

import engine.ecs.*;

import java.util.List;

public class UISystem implements GameSystem {
    private final InteractionSystem interactionSystem;
    
    public UISystem(InteractionSystem interactionSystem) {
        this.interactionSystem = interactionSystem;
    }
    
    @Override
    public void update(List<Entity> entities, float deltaTime) {
        // UI updates handled in render method
    }
    
    public void render() {
        renderControls();
        renderMessageBox();
    }
    
    private void renderControls() {
        System.out.print("🎮 WASD: Move | SPACE: Interact | ESC: Quit ");
    }
    
    private void renderMessageBox() {
        if (interactionSystem.hasMessage()) {
            System.out.print("📝 [" + interactionSystem.getCurrentMessage() + "] ");
        }
    }
}
