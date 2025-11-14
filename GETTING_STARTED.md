# 🎮 Getting Started - Build Your First RPG

## 🚀 Your First 5 Minutes

### 1. Clone & Run
```bash
git clone https://github.com/Pognar/2d.git
cd 2d
./build.sh
java -cp target/classes engine.core.GameEngine
```

**🎯 You should see:** Jacket's bedroom with WASD movement!

### 2. Understanding What You Built
- **Yellow square** = Jacket (the player)
- **Brown square** = Debug Teddy (interactive)
- **Gray squares** = Stone walls (solid)
- **Green squares** = Grass floor (walkable)

## 🏗️ Tutorial: Create Your Own Room

### Step 1: Create a New Game State
```java
// src/main/java/game/states/MyRoomState.java
package game.states;

import engine.core.GameState;
import engine.ecs.*;
import engine.graphics.*;
import engine.input.InputManager;
import game.systems.*;

import java.util.ArrayList;
import java.util.List;

public class MyRoomState implements GameState {
    private final List<Entity> entities = new ArrayList<>();
    private final InputManager inputManager;
    private final RenderEngine renderEngine;
    private final TileMap room = new TileMap(12, 8, 32); // 12x8 room
    private final Camera camera = new Camera(800, 600);
    
    private MovementSystem movementSystem;
    private InteractionSystem interactionSystem;
    private UISystem uiSystem;
    
    public MyRoomState(InputManager inputManager) {
        this.inputManager = inputManager;
        this.renderEngine = new RenderEngine(inputManager);
    }
    
    @Override
    public void enter() {
        System.out.println("🏠 Welcome to your custom room!");
        
        // Create room layout
        createRoom();
        
        // Create player
        Entity player = new Entity(1);
        player.addComponent(new PositionComponent(96, 128)); // Center of room
        player.addComponent(new RenderComponent("jacket.png", 32, 32));
        player.addComponent(new InputComponent(true));
        entities.add(player);
        
        // Create interactive object
        Entity treasure = new Entity(2);
        treasure.addComponent(new PositionComponent(320, 96));
        treasure.addComponent(new RenderComponent("treasure.png", 32, 32));
        treasure.addComponent(new InteractionComponent("You found a treasure chest!"));
        entities.add(treasure);
        
        // Setup camera
        camera.setTarget(player);
        
        // Initialize systems
        movementSystem = new MovementSystem(inputManager, room);
        interactionSystem = new InteractionSystem(inputManager);
        uiSystem = new UISystem(interactionSystem);
    }
    
    private void createRoom() {
        // Build walls around perimeter
        for (int x = 0; x < 12; x++) {
            room.setTile(x, 0, Tile.STONE);  // Top wall
            room.setTile(x, 7, Tile.STONE);  // Bottom wall
        }
        for (int y = 0; y < 8; y++) {
            room.setTile(0, y, Tile.STONE);  // Left wall
            room.setTile(11, y, Tile.STONE); // Right wall
        }
        
        // Add some furniture
        room.setTile(2, 2, Tile.TREE);   // Table
        room.setTile(9, 5, Tile.TREE);   // Chair
        room.setTile(6, 0, Tile.WATER);  // Window
    }
    
    @Override
    public void update(float deltaTime) {
        movementSystem.update(entities, deltaTime);
        interactionSystem.update(entities, deltaTime);
        uiSystem.update(entities, deltaTime);
        camera.update();
    }
    
    @Override
    public void render() {
        renderEngine.clear();
        renderEngine.renderTileMap(room, camera);
        renderEngine.render(entities, camera);
        renderEngine.present();
        uiSystem.render();
    }
    
    @Override
    public void exit() {
        System.out.println("👋 Leaving your room...");
    }
}
```

### Step 2: Use Your New State
```java
// In GameEngine.java, replace BedroomDemoState with:
stateManager.pushState(new MyRoomState(inputManager));
```

### Step 3: Build & Test
```bash
./build.sh
java -cp target/classes engine.core.GameEngine
```

## 🎯 Tutorial: Add a Wandering NPC

```java
// Add this to your enter() method:
Entity villager = new Entity(3);
villager.addComponent(new PositionComponent(160, 160));
villager.addComponent(new RenderComponent("villager.png", 32, 32));
villager.addComponent(new AIComponent(AIComponent.AIBehavior.WANDER, 30.0f, 0.0f));
villager.addComponent(new InteractionComponent("Hello, I'm just wandering around!"));
entities.add(villager);

// Add AISystem to your systems:
private AISystem aiSystem;

// In enter():
aiSystem = new AISystem();

// In update():
aiSystem.update(entities, deltaTime);
```

## 🎨 Tutorial: Create a Multi-Room Adventure

### Room Connection System
```java
public class AdventureState implements GameState {
    private TileMap currentRoom;
    private int roomId = 1;
    
    private void switchRoom(int newRoomId) {
        roomId = newRoomId;
        entities.clear(); // Remove old entities
        
        switch (roomId) {
            case 1 -> createBedroom();
            case 2 -> createKitchen();
            case 3 -> createGarden();
        }
        
        // Recreate player in new room
        createPlayer();
    }
    
    private void createBedroom() {
        currentRoom = new TileMap(10, 8, 32);
        // Build bedroom...
        
        // Add door to kitchen
        Entity door = new Entity(10);
        door.addComponent(new PositionComponent(288, 224));
        door.addComponent(new RenderComponent("door.png", 32, 32));
        door.addComponent(new InteractionComponent("Enter kitchen"));
        entities.add(door);
    }
}
```

## 🎵 Next Steps - Make It Your Own!

### 1. **Visual Upgrades**
- Replace colored squares with actual sprites
- Add sprite animations for walking
- Create tile sets for different environments

### 2. **Gameplay Features**
- **Inventory system** - Collect and use items
- **Dialogue trees** - Rich NPC conversations  
- **Quest system** - Goals and objectives
- **Combat system** - Turn-based battles

### 3. **World Building**
- **Multiple levels** - Dungeons, towns, overworld
- **Save/Load** - Persistent game progress
- **Sound effects** - Audio feedback
- **Music system** - Background tracks

### 4. **Advanced Systems**
- **Particle effects** - Magic spells, explosions
- **Lighting system** - Dynamic shadows
- **Weather effects** - Rain, snow, fog
- **Day/night cycle** - Time-based events

## 📚 Learning Resources

- **PROGRAMMING_GUIDE.md** - Deep dive into engine architecture
- **API_GUIDE.md** - Complete API reference
- **BedroomDemoState.java** - Example implementation
- **GitHub Issues** - Ask questions and get help

## 🎮 Community

Share your creations! Fork the repo and show us what amazing worlds you build with the engine.

**Happy game development! 🚀✨**
