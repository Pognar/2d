# 🔧 2D RPG Engine - API Reference
Just some helpful stuff to get people going and for me to remember
## 📦 Core Classes

### GameEngine
```java
public class GameEngine {
    public void run()                    // Start the game loop
    public static void main(String[])    // Entry point
}
```

### StateManager
```java
public class StateManager {
    public void pushState(GameState state)     // Add new state
    public void popState()                     // Remove current state
    public void update(float deltaTime)       // Update active state
    public void render()                       // Render active state
}
```

### GameState Interface
```java
public interface GameState {
    void enter()                    // Called when state becomes active
    void update(float deltaTime)    // Called every frame
    void render()                   // Called every frame for drawing
    void exit()                     // Called when state is removed
}
```

## 🎯 Entity Component System

### Entity
```java
public class Entity {
    Entity(int id)                              // Create with unique ID
    void addComponent(Component component)      // Attach component
    <T> T getComponent(Class<T> type)          // Get component by type
    boolean hasComponent(Class<?> type)         // Check if has component
    void removeComponent(Class<?> type)         // Remove component
}
```

### Components
```java
// Position in world
public record PositionComponent(float x, float y) implements Component {}

// Visual representation
public record RenderComponent(String texturePath, float width, float height) implements Component {}

// Keyboard control
public record InputComponent(boolean controllable) implements Component {}

// Interactive object
public record InteractionComponent(String message) implements Component {}

// AI behavior
public record AIComponent(AIBehavior behavior, float speed, float timer) implements Component {
    public enum AIBehavior { IDLE, WANDER }
}

// Animation frames
public record AnimationComponent(String[] frames, float frameTime, boolean loop, 
                               int currentFrame, float timer) implements Component {}
```

## 🎮 Input System

### InputManager
```java
public class InputManager {
    void init()                              // Initialize input system
    void update()                            // Update key states
    boolean isKeyPressed(int keyCode)        // Check if key currently pressed
    void setKeyPressed(int keyCode, boolean pressed)  // Set key state (for window events)
}
```

### Key Codes
```java
// Movement
KeyEvent.VK_W = 87      // Up
KeyEvent.VK_A = 65      // Left  
KeyEvent.VK_S = 83      // Down
KeyEvent.VK_D = 68      // Right

// Actions
KeyEvent.VK_SPACE = 32  // Interact
KeyEvent.VK_ESCAPE = 27 // Quit
```

## 🎨 Graphics System

### RenderEngine
```java
public class RenderEngine {
    RenderEngine(InputManager inputManager)                    // Create with input handling
    void renderTileMap(TileMap tileMap, Camera camera)        // Draw world tiles
    void render(List<Entity> entities, Camera camera)         // Draw entities
    void clear()                                              // Clear screen
    void present()                                            // Display frame
    void setProjection(int width, int height)                 // Set viewport
}
```

### GameWindow
```java
public class GameWindow extends JPanel implements KeyListener {
    GameWindow(InputManager inputManager)                     // Create window with input
    void clear()                                             // Clear to black
    void drawTile(String texture, float x, float y, int size) // Draw tile
    void drawEntity(String texture, float x, float y, float w, float h) // Draw entity
    void present()                                           // Refresh display
}
```

### Camera
```java
public class Camera {
    Camera(int viewWidth, int viewHeight)    // Create camera
    void setTarget(Entity target)            // Follow entity
    void update()                           // Update position
    float getX()                            // Get camera X
    float getY()                            // Get camera Y
    int getViewWidth()                      // Get viewport width
    int getViewHeight()                     // Get viewport height
}
```

### TileMap
```java
public class TileMap {
    TileMap(int width, int height, int tileSize)  // Create map
    void setTile(int x, int y, Tile tile)         // Place tile
    Tile getTile(int x, int y)                    // Get tile at position
    boolean isBlocked(float x, float y)           // Check collision
    int getWidth()                                // Map width in tiles
    int getHeight()                               // Map height in tiles
    int getTileSize()                             // Tile size in pixels
}
```

### Tile Enum
```java
public enum Tile {
    GRASS("grass.png", false),    // Walkable green floor
    STONE("stone.png", true),     // Solid gray wall
    WATER("water.png", true),     // Solid blue decoration
    TREE("tree.png", true);       // Solid brown object
    
    String texturePath()          // Get sprite filename
    boolean isBlocked()           // Check if solid
}
```

## ⚙️ Game Systems

### MovementSystem
```java
public class MovementSystem implements GameSystem {
    MovementSystem(InputManager input, TileMap world)  // Create with dependencies
    void update(List<Entity> entities, float deltaTime) // Process movement
}
```

### InteractionSystem  
```java
public class InteractionSystem implements GameSystem {
    InteractionSystem(InputManager input)               // Create with input
    void update(List<Entity> entities, float deltaTime) // Check interactions
    String getCurrentMessage()                          // Get active message
}
```

### UISystem
```java
public class UISystem implements GameSystem {
    UISystem(InteractionSystem interactions)            // Create with interaction system
    void update(List<Entity> entities, float deltaTime) // Update UI state
    void render()                                       // Draw UI to console
}
```

### AISystem
```java
public class AISystem implements GameSystem {
    void update(List<Entity> entities, float deltaTime) // Update AI behaviors
}
```

### AnimationSystem
```java
public class AnimationSystem implements GameSystem {
    void update(List<Entity> entities, float deltaTime) // Update animation frames
}
```

## 🎯 Usage Examples

### Create Player
```java
Entity player = new Entity(1);
player.addComponent(new PositionComponent(100, 100));
player.addComponent(new RenderComponent("jacket.png", 32, 32));
player.addComponent(new InputComponent(true));
```

### Create Interactive NPC
```java
Entity npc = new Entity(2);
npc.addComponent(new PositionComponent(200, 200));
npc.addComponent(new RenderComponent("teddy_bear.png", 32, 32));
npc.addComponent(new InteractionComponent("Hello there!"));
```

### Create Wandering NPC
```java
Entity wanderer = new Entity(3);
wanderer.addComponent(new PositionComponent(300, 300));
wanderer.addComponent(new RenderComponent("villager.png", 32, 32));
wanderer.addComponent(new AIComponent(AIComponent.AIBehavior.WANDER, 50.0f, 0.0f));
```

### Build World
```java
TileMap world = new TileMap(20, 15, 32);

// Create walls
for (int x = 0; x < 20; x++) {
    world.setTile(x, 0, Tile.STONE);      // Top wall
    world.setTile(x, 14, Tile.STONE);     // Bottom wall
}

// Add decorations
world.setTile(10, 7, Tile.WATER);        // Window
world.setTile(5, 12, Tile.TREE);         // Furniture
```

---

**Complete API for building 2D RPG adventures! 🎮**
