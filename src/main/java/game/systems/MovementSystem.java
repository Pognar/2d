package game.systems;

import engine.ecs.*;
import engine.input.InputManager;
import engine.graphics.TileMap;

import java.util.List;

public class MovementSystem implements GameSystem {
    private final InputManager inputManager;
    private final TileMap tileMap;
    private final float moveSpeed = 100f; // pixels per second
    
    public MovementSystem(InputManager inputManager, TileMap tileMap) {
        this.inputManager = inputManager;
        this.tileMap = tileMap;
    }
    
    @Override
    public void update(List<Entity> entities, float deltaTime) {
        for (Entity entity : entities) {
            if (entity.hasComponent(PositionComponent.class) && 
                entity.hasComponent(InputComponent.class)) {
                
                var input = entity.getComponent(InputComponent.class);
                if (!input.controllable()) continue;
                
                var pos = entity.getComponent(PositionComponent.class);
                float newX = pos.x();
                float newY = pos.y();
                
                // 4-directional movement (WASD keys: 87=W, 65=A, 83=S, 68=D)
                if (inputManager.isKeyPressed(87)) newY -= moveSpeed * deltaTime; // W
                if (inputManager.isKeyPressed(83)) newY += moveSpeed * deltaTime; // S
                if (inputManager.isKeyPressed(65)) newX -= moveSpeed * deltaTime; // A
                if (inputManager.isKeyPressed(68)) newX += moveSpeed * deltaTime; // D
                
                // Check collision
                if (canMoveTo(newX, newY)) {
                    entity.addComponent(new PositionComponent(newX, newY));
                }
            }
        }
    }
    
    private boolean canMoveTo(float x, float y) {
        int tileX = (int) (x / tileMap.getTileSize());
        int tileY = (int) (y / tileMap.getTileSize());
        return !tileMap.isSolid(tileX, tileY);
    }
}
