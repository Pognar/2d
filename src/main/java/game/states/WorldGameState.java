package game.states;

import engine.core.GameState;
import engine.ecs.*;
import engine.graphics.*;
import engine.input.InputManager;
import game.systems.*;

import java.util.ArrayList;
import java.util.List;

public class WorldGameState implements GameState {
    private final List<Entity> entities = new ArrayList<>();
    private final InputManager inputManager;
    private final RenderEngine renderEngine;
    private final TileMap tileMap = new TileMap(50, 50, 32);
    private final Camera camera = new Camera(800, 600);
    
    private MovementSystem movementSystem;
    private AnimationSystem animationSystem;
    private AISystem aiSystem;
    
    private Entity player;
    
    public WorldGameState(InputManager inputManager) {
        this.inputManager = inputManager;
        this.renderEngine = new RenderEngine(inputManager);
    }
    
    @Override
    public void enter() {
        System.out.println("🌍 Entering world - The adventure begins!");
        
        renderEngine.setProjection(800, 600);
        
        // Create some obstacles
        tileMap.setTile(10, 10, Tile.STONE);
        tileMap.setTile(15, 8, Tile.TREE);
        tileMap.setTile(20, 12, Tile.WATER);
        
        // Create player
        player = new Entity(1);
        player.addComponent(new PositionComponent(400, 300));
        player.addComponent(new RenderComponent("player.png", 32, 32));
        player.addComponent(new InputComponent(true));
        
        String[] walkFrames = {"player_walk1.png", "player_walk2.png", "player_walk3.png"};
        player.addComponent(new AnimationComponent(walkFrames, 0.2f, true));
        
        entities.add(player);
        camera.setTarget(player);
        
        // Create NPCs
        Entity npc1 = new Entity(2);
        npc1.addComponent(new PositionComponent(500, 400));
        npc1.addComponent(new RenderComponent("npc.png", 32, 32));
        npc1.addComponent(new AIComponent(AIComponent.AIBehavior.WANDER));
        entities.add(npc1);
        
        Entity npc2 = new Entity(3);
        npc2.addComponent(new PositionComponent(300, 200));
        npc2.addComponent(new RenderComponent("guard.png", 32, 32));
        npc2.addComponent(new AIComponent(AIComponent.AIBehavior.IDLE));
        entities.add(npc2);
        
        // Initialize systems
        movementSystem = new MovementSystem(inputManager, tileMap);
        animationSystem = new AnimationSystem();
        aiSystem = new AISystem();
        
        System.out.println("🎮 World loaded! Use WASD to move your character!");
    }
    
    @Override
    public void update(float deltaTime) {
        // Update systems
        movementSystem.update(entities, deltaTime);
        aiSystem.update(entities, deltaTime);
        animationSystem.update(entities, deltaTime);
        
        // Update camera
        camera.update();
    }
    
    @Override
    public void render() {
        System.out.print("🗺️  ");
        renderEngine.renderTileMap(tileMap, camera);
        System.out.print("| ");
        renderEngine.render(entities, camera);
        System.out.println();
    }
    
    @Override
    public void exit() {
        System.out.println("👋 Leaving the world...");
    }
}
