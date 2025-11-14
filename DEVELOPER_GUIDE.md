# 2D RPG Engine - Developer Guide

## Quick Start

```bash
# Build the engine
./build.sh

# Run the engine
java -cp target/classes engine.core.GameEngine
```

## Dependencies

- **Java 21+** (required)
- **No external libraries** (self-contained)

## Architecture Overview

### Entity Component System (ECS)
```java
// Create an entity
Entity player = new Entity(1);

// Add components
player.addComponent(new PositionComponent(100, 100));
player.addComponent(new RenderComponent("player.png", 32, 32));
player.addComponent(new InputComponent(true));
```

### Available Components

| Component | Purpose | Constructor |
|-----------|---------|-------------|
| `PositionComponent` | World position | `(float x, float y)` |
| `RenderComponent` | Sprite rendering | `(String texture, int width, int height)` |
| `InputComponent` | Player control | `(boolean controllable)` |
| `AnimationComponent` | Sprite animation | `(String[] frames, float frameTime, boolean loop)` |
| `AIComponent` | NPC behavior | `(AIBehavior behavior)` |

### Game Systems

| System | Purpose | Usage |
|--------|---------|-------|
| `MovementSystem` | Player movement | Handles WASD input + collision |
| `AnimationSystem` | Sprite animation | Updates animation frames |
| `AISystem` | NPC behavior | WANDER, IDLE behaviors |

## Creating a Game

### 1. Create a Game State
```java
public class MyGameState implements GameState {
    private final List<Entity> entities = new ArrayList<>();
    private final TileMap tileMap = new TileMap(20, 20, 32);
    private final Camera camera = new Camera(800, 600);
    
    @Override
    public void enter() {
        // Initialize your game world
        Entity player = new Entity(1);
        player.addComponent(new PositionComponent(400, 300));
        player.addComponent(new RenderComponent("hero.png", 32, 32));
        entities.add(player);
    }
    
    @Override
    public void update(float deltaTime) {
        // Update game logic
    }
    
    @Override
    public void render() {
        // Render game world
    }
}
```

### 2. Set Up Tile Maps
```java
// Create a 50x50 world with 32px tiles
TileMap world = new TileMap(50, 50, 32);

// Place obstacles
world.setTile(10, 10, Tile.STONE);  // Solid wall
world.setTile(15, 8, Tile.TREE);    // Solid tree
world.setTile(20, 12, Tile.WATER);  // Solid water
```

### 3. Add Player Movement
```java
// Make entity controllable
player.addComponent(new InputComponent(true));

// Add movement system
MovementSystem movement = new MovementSystem(inputManager, tileMap);
movement.update(entities, deltaTime);
```

### 4. Create NPCs
```java
Entity npc = new Entity(2);
npc.addComponent(new PositionComponent(500, 400));
npc.addComponent(new RenderComponent("villager.png", 32, 32));
npc.addComponent(new AIComponent(AIComponent.AIBehavior.WANDER));
```

### 5. Add Animations
```java
String[] walkFrames = {"walk1.png", "walk2.png", "walk3.png"};
player.addComponent(new AnimationComponent(walkFrames, 0.2f, true));

// Update animations
AnimationSystem animations = new AnimationSystem();
animations.update(entities, deltaTime);
```

## Camera System

```java
Camera camera = new Camera(800, 600);
camera.setTarget(player);  // Follow player
camera.update();           // Smooth following

// Render with camera offset
renderEngine.render(entities, camera);
renderEngine.renderTileMap(tileMap, camera);
```

## Input Controls

| Key | Action |
|-----|--------|
| W | Move up |
| A | Move left |
| S | Move down |
| D | Move right |

## Tile Types

```java
Tile.GRASS  // Walkable ground
Tile.STONE  // Solid wall
Tile.TREE   // Solid obstacle  
Tile.WATER  // Solid liquid
```

## AI Behaviors

```java
AIComponent.AIBehavior.IDLE    // Stay in place
AIComponent.AIBehavior.WANDER  // Move randomly every 2 seconds
```

## Performance Tips

- **Tile Culling**: Only visible tiles are rendered
- **Entity Culling**: Only on-screen entities are rendered  
- **Component Updates**: Systems only process relevant entities
- **Memory Efficient**: Records and sealed interfaces minimize overhead

## Example: Complete Game Setup

```java
public class RPGGameState implements GameState {
    private final List<Entity> entities = new ArrayList<>();
    private final TileMap world = new TileMap(30, 30, 32);
    private final Camera camera = new Camera(800, 600);
    private final RenderEngine renderer = new RenderEngine();
    private final InputManager input = new InputManager();
    
    private MovementSystem movement;
    private AnimationSystem animations;
    private AISystem ai;
    
    @Override
    public void enter() {
        // Create world
        world.setTile(5, 5, Tile.STONE);
        world.setTile(10, 8, Tile.TREE);
        
        // Create player
        Entity hero = new Entity(1);
        hero.addComponent(new PositionComponent(400, 300));
        hero.addComponent(new RenderComponent("hero.png", 32, 32));
        hero.addComponent(new InputComponent(true));
        
        String[] heroWalk = {"hero1.png", "hero2.png"};
        hero.addComponent(new AnimationComponent(heroWalk, 0.3f, true));
        
        entities.add(hero);
        camera.setTarget(hero);
        
        // Create NPC
        Entity merchant = new Entity(2);
        merchant.addComponent(new PositionComponent(200, 200));
        merchant.addComponent(new RenderComponent("merchant.png", 32, 32));
        merchant.addComponent(new AIComponent(AIComponent.AIBehavior.WANDER));
        entities.add(merchant);
        
        // Initialize systems
        movement = new MovementSystem(input, world);
        animations = new AnimationSystem();
        ai = new AISystem();
    }
    
    @Override
    public void update(float deltaTime) {
        movement.update(entities, deltaTime);
        ai.update(entities, deltaTime);
        animations.update(entities, deltaTime);
        camera.update();
    }
    
    @Override
    public void render() {
        renderer.renderTileMap(world, camera);
        renderer.render(entities, camera);
    }
}
```

## Build System

The engine uses a simple build script:

```bash
#!/bin/bash
mkdir -p target/classes
cd src/main/java
javac -d ../../../target/classes $(find . -name "*.java")
```

## Next Steps

Ready to implement:
- **Dialogue System**: NPC conversations
- **Combat System**: Turn-based battles  
- **Inventory System**: Item management
- **Save/Load System**: Game persistence
- **UI System**: Menus and interfaces

## Troubleshooting

**Build Issues**: Ensure Java 21+ is installed
**Runtime Issues**: Check classpath includes `target/classes`
**Performance**: Reduce map size or entity count for testing
