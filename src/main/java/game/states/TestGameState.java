package game.states;

import engine.core.GameState;
import engine.ecs.Entity;
import engine.ecs.PositionComponent;
import engine.ecs.RenderComponent;
import engine.graphics.RenderEngine;
import engine.graphics.Camera;
import engine.input.InputManager;

import java.util.ArrayList;
import java.util.List;

public class TestGameState implements GameState {
    private final List<Entity> entities = new ArrayList<>();
    private final RenderEngine renderEngine;
    private final Camera camera = new Camera(800, 600);
    
    public TestGameState(InputManager inputManager) {
        this.renderEngine = new RenderEngine(inputManager);
    }
    
    @Override
    public void enter() {
        System.out.println("Entering test game state");
        
        // Create a test entity
        Entity player = new Entity(1);
        player.addComponent(new PositionComponent(100, 100));
        player.addComponent(new RenderComponent("player.png", 32, 32));
        entities.add(player);
        
        renderEngine.setProjection(800, 600);
    }
    
    @Override
    public void update(float deltaTime) {
        // Move player slightly each frame
        for (Entity entity : entities) {
            if (entity.hasComponent(PositionComponent.class)) {
                var pos = entity.getComponent(PositionComponent.class);
                var newPos = new PositionComponent(pos.x() + deltaTime * 10, pos.y());
                entity.addComponent(newPos);
            }
        }
    }
    
    @Override
    public void render() {
        renderEngine.render(entities, camera);
    }
    
    @Override
    public void exit() {
        System.out.println("Exiting test game state");
    }
}
