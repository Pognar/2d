package game.states;

import engine.core.GameState;
import engine.ecs.*;
import engine.graphics.*;
import engine.input.InputManager;
import game.systems.*;

import java.util.ArrayList;
import java.util.List;

public class BedroomDemoState implements GameState {
    private final List<Entity> entities = new ArrayList<>();
    private final InputManager inputManager;
    private final RenderEngine renderEngine;
    private final TileMap bedroom = new TileMap(10, 8, 32); // 10x8 bedroom
    private final Camera camera = new Camera(800, 600);
    
    private MovementSystem movementSystem;
    private InteractionSystem interactionSystem;
    private UISystem uiSystem;
    
    private Entity player;
    
    public BedroomDemoState(InputManager inputManager) {
        this.inputManager = inputManager;
        this.renderEngine = new RenderEngine(inputManager);
    }
    
    @Override
    public void enter() {
        System.out.println("🏠 Entering Bedroom Demo - Welcome to your room!");
        
        renderEngine.setProjection(800, 600);
        
        // Create bedroom layout
        createBedroomLayout();
        
        // Create player (spawn on bed)
        player = new Entity(1);
        player.addComponent(new PositionComponent(64, 192)); // Bed position (2,6 in tiles)
        player.addComponent(new RenderComponent("player_down.png", 32, 32));
        player.addComponent(new InputComponent(true));
        entities.add(player);
        
        // Create teddy bear (interactive object)
        Entity teddyBear = new Entity(2);
        teddyBear.addComponent(new PositionComponent(128, 96)); // Position (4,3 in tiles)
        teddyBear.addComponent(new RenderComponent("teddy_bear.png", 32, 32));
        teddyBear.addComponent(new InteractionComponent("It's my favorite teddy bear!"));
        entities.add(teddyBear);
        
        // Set camera to follow player
        camera.setTarget(player);
        
        // Initialize systems
        movementSystem = new MovementSystem(inputManager, bedroom);
        interactionSystem = new InteractionSystem(inputManager);
        uiSystem = new UISystem(interactionSystem);
        
        System.out.println("🎮 Demo loaded! Use WASD to move, SPACE to interact with teddy bear!");
    }
    
    private void createBedroomLayout() {
        // Create walls around perimeter
        for (int x = 0; x < 10; x++) {
            bedroom.setTile(x, 0, Tile.STONE); // Top wall
            bedroom.setTile(x, 7, Tile.STONE); // Bottom wall
        }
        for (int y = 0; y < 8; y++) {
            bedroom.setTile(0, y, Tile.STONE); // Left wall
            bedroom.setTile(9, y, Tile.STONE); // Right wall
        }
        
        // Floor is already grass (default), represents wooden floor
        
        // Place bed (2x1 tiles at bottom left)
        bedroom.setTile(1, 6, Tile.TREE); // Bed tile (using tree as placeholder)
        bedroom.setTile(2, 6, Tile.TREE); // Bed tile
        
        // Place window decoration (top wall)
        bedroom.setTile(7, 0, Tile.WATER); // Window (using water as placeholder)
    }
    
    @Override
    public void update(float deltaTime) {
        // Update systems
        movementSystem.update(entities, deltaTime);
        interactionSystem.update(entities, deltaTime);
        uiSystem.update(entities, deltaTime);
        
        // Update camera
        camera.update();
    }
    
    @Override
    public void render() {
        renderEngine.clear();
        renderEngine.renderTileMap(bedroom, camera);
        renderEngine.render(entities, camera);
        renderEngine.present();
        
        // Still show UI in console for now
        uiSystem.render();
    }
    
    @Override
    public void exit() {
        System.out.println("👋 Leaving the bedroom...");
    }
}
