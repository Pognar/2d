# 🎮 2D RPG Engine - Programming Guide

## 🚀 Quick Start for Developers

### Building & Running
```bash
./build.sh                                    # Compile everything
java -cp target/classes engine.core.GameEngine  # Run Jacket's demo
```

## 🏗️ Architecture Overview

### Entity Component System (ECS)
```java
// Create an entity
Entity player = new Entity(1);
player.addComponent(new PositionComponent(100, 100));
player.addComponent(new RenderComponent("jacket.png", 32, 32));
player.addComponent(new InputComponent(true));
```

### Core Components
- **PositionComponent** - X/Y coordinates
- **RenderComponent** - Sprite path, width, height
- **InputComponent** - Keyboard control flag
- **InteractionComponent** - Message for SPACE key interactions
- **AIComponent** - NPC behavior (WANDER, IDLE)
- **AnimationComponent** - Frame-based animations

### Systems
- **MovementSystem** - WASD input → position updates
- **InteractionSystem** - SPACE key proximity detection
- **UISystem** - Console output for controls/messages
- **AISystem** - NPC movement patterns
- **AnimationSystem** - Sprite frame cycling

## 🎨 Creating New Game States

```java
public class MyGameState implements GameState {
    private final List<Entity> entities = new ArrayList<>();
    private final InputManager inputManager;
    private final RenderEngine renderEngine;
    
    public MyGameState(InputManager inputManager) {
        this.inputManager = inputManager;
        this.renderEngine = new RenderEngine(inputManager);
    }
    
    @Override
    public void enter() {
        // Initialize your world here
    }
    
    @Override
    public void update(float deltaTime) {
        // Update game logic
    }
    
    @Override
    public void render() {
        renderEngine.clear();
        renderEngine.renderTileMap(tileMap, camera);
        renderEngine.render(entities, camera);
        renderEngine.present();
    }
}
```

## 🗺️ World Building

### Creating Tile Maps
```java
TileMap world = new TileMap(20, 15, 32); // 20x15 tiles, 32px each

// Set tiles
world.setTile(0, 0, Tile.STONE);  // Wall
world.setTile(1, 1, Tile.GRASS);  // Floor
world.setTile(5, 5, Tile.WATER);  // Obstacle
```

### Available Tiles
- `Tile.STONE` - Gray walls (solid)
- `Tile.GRASS` - Green floor (walkable)
- `Tile.TREE` - Brown objects (solid)
- `Tile.WATER` - Blue decorations (solid)

## 🎯 Adding Interactive Objects

```java
Entity npc = new Entity(2);
npc.addComponent(new PositionComponent(200, 150));
npc.addComponent(new RenderComponent("npc.png", 32, 32));
npc.addComponent(new InteractionComponent("Hello, traveler!"));
entities.add(npc);
```

## 🎮 Input Handling

### Key Codes
- **W/A/S/D** - Movement (87/65/83/68)
- **SPACE** - Interact (32)
- **ESC** - Quit (27)

### Custom Input
```java
if (inputManager.isKeyPressed(KeyEvent.VK_E)) {
    // Custom action
}
```

## 📱 Graphics System

### Colors (Current Palette)
```java
// In GameWindow.java
tileColors.put("stone.png", new Color(128, 128, 128));    // Gray
tileColors.put("grass.png", new Color(34, 139, 34));      // Green
tileColors.put("jacket.png", new Color(107, 70, 193));    // Purple
```

### Adding New Sprites
1. Add color mapping in `GameWindow.initializeTileColors()`
2. Use 32x32 pixel sprites
3. Reference by filename: `"my_sprite.png"`

## 🔧 Extending the Engine

### New Component Type
```java
public record HealthComponent(int current, int max) implements Component {}
```

### New System
```java
public class HealthSystem implements GameSystem {
    @Override
    public void update(List<Entity> entities, float deltaTime) {
        for (Entity entity : entities) {
            if (entity.hasComponent(HealthComponent.class)) {
                // Health logic here
            }
        }
    }
}
```

## 🎪 Camera System

```java
Camera camera = new Camera(800, 600);
camera.setTarget(playerEntity);  // Follow player
camera.update();                 // Call each frame
```

## 🐛 Debugging Tips

### Console Output
- Movement: Watch position changes in console
- Interactions: Messages appear when SPACE pressed near objects
- Collisions: Check tile collision detection

### Common Issues
- **No movement**: Check InputManager key mappings
- **No graphics**: Verify GameWindow initialization
- **Crashes**: Ensure all components are properly added

## 📁 Project Structure

```
src/main/java/
├── engine/
│   ├── core/          # GameEngine, StateManager
│   ├── ecs/           # Components, Entity, Systems
│   ├── graphics/      # Rendering, Camera, Tiles
│   └── input/         # InputManager
└── game/
    ├── states/        # Game scenes (BedroomDemo, etc.)
    └── systems/       # Game-specific systems
```

## 🎯 Performance Notes

- **60 FPS target** - 16ms frame time
- **Tile culling** - Only renders visible tiles
- **Entity culling** - Only renders on-screen entities
- **Simple graphics** - Colored rectangles for performance

## 🚀 Next Steps

1. **Add sprites** - Replace colored squares with actual images
2. **Sound system** - Audio effects and music
3. **Save/Load** - Game state persistence
4. **Dialogue system** - Rich NPC conversations
5. **Combat system** - Turn-based battles
6. **Inventory** - Item management

---

**Happy coding! Build amazing worlds with Jacket! 🎮✨**
