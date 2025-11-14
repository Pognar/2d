package game.systems;

import engine.ecs.*;
import engine.input.InputManager;

import java.util.List;

public class InteractionSystem implements GameSystem {
    private final InputManager inputManager;
    private String currentMessage = null;
    private float messageTimer = 0f;
    private final float MESSAGE_DURATION = 3f; // 3 seconds
    
    public InteractionSystem(InputManager inputManager) {
        this.inputManager = inputManager;
    }
    
    @Override
    public void update(List<Entity> entities, float deltaTime) {
        // Update message timer
        if (currentMessage != null) {
            messageTimer -= deltaTime;
            if (messageTimer <= 0) {
                currentMessage = null;
            }
        }
        
        // Check for SPACE key interaction (32 = SPACE)
        if (inputManager.isKeyPressed(32)) {
            Entity player = findPlayer(entities);
            if (player != null) {
                Entity interactable = findNearbyInteractable(entities, player);
                if (interactable != null) {
                    var interaction = interactable.getComponent(InteractionComponent.class);
                    showMessage(interaction.message());
                }
            }
        }
    }
    
    private Entity findPlayer(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.hasComponent(InputComponent.class) && 
                entity.getComponent(InputComponent.class).controllable()) {
                return entity;
            }
        }
        return null;
    }
    
    private Entity findNearbyInteractable(List<Entity> entities, Entity player) {
        var playerPos = player.getComponent(PositionComponent.class);
        
        for (Entity entity : entities) {
            if (entity.hasComponent(InteractionComponent.class) && 
                entity.hasComponent(PositionComponent.class)) {
                
                var entityPos = entity.getComponent(PositionComponent.class);
                float distance = Math.abs(playerPos.x() - entityPos.x()) + 
                               Math.abs(playerPos.y() - entityPos.y());
                
                if (distance <= 48f) { // Within 1.5 tiles (32px * 1.5)
                    return entity;
                }
            }
        }
        return null;
    }
    
    private void showMessage(String message) {
        currentMessage = message;
        messageTimer = MESSAGE_DURATION;
        System.out.println("💬 " + message); // Console output for now
    }
    
    public String getCurrentMessage() {
        return currentMessage;
    }
    
    public boolean hasMessage() {
        return currentMessage != null;
    }
}
