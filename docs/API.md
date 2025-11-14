# API Reference

## Core Engine

### GameEngine

Main engine class that manages the game loop and core systems.

```java
public class GameEngine {
    public void start()                    // Initialize and start the game
    public void stop()                     // Shutdown the engine
    public void setState(GameState state)  // Change current game state
    public float getDeltaTime()            // Get frame delta time
    public boolean isRunning()             // Check if engine is running
}
```

### StateManager

Manages game state transitions and lifecycle.

```java
public class StateManager {
    public void pushState(GameState state)     // Push new state onto stack
    public void popState()                     // Remove current state
    public void changeState(GameState state)  // Replace current state
    public GameState getCurrentState()         // Get active state
}
```

## Entity Component System

### World

Container for all entities and systems.

```java
public class World {
    public Entity createEntity()                    // Create new entity
    public void destroyEntity(Entity entity)       // Remove entity
    public void addSystem(System system)           // Register system
    public void update(float deltaTime)            // Update all systems
    public List<Entity> getEntities(Class<?>... componentTypes)  // Query entities
}
```

### Entity

Unique identifier with component container.

```java
public class Entity {
    public <T extends Component> void add(T component)     // Add component
    public <T extends Component> T get(Class<T> type)      // Get component
    public <T extends Component> void remove(Class<T> type) // Remove component
    public boolean has(Class<? extends Component> type)    // Check component
    public int getId()                                      // Get unique ID
}
```

### Components

Data containers using records for immutability.

```java
// Position and movement
public record PositionComponent(float x, float y) implements Component {}
public record VelocityComponent(float dx, float dy) implements Component {}

// Graphics
public record SpriteComponent(String texture, int frame) implements Component {}
public record AnimationComponent(String name, float time) implements Component {}

// Game logic
public record StatsComponent(int health, int attack, int defense) implements Component {}
public record InventoryComponent(List<Item> items) implements Component {}

// Physics
public record CollisionComponent(float width, float height, boolean solid) implements Component {}
```

### Systems

Process entities with specific component combinations.

```java
public abstract class System {
    protected World world;
    public abstract void update(float deltaTime);
    protected List<Entity> getEntities(Class<?>... componentTypes);
}

// Example implementations
public class MovementSystem extends System         // Handle entity movement
public class RenderSystem extends System          // Draw sprites
public class CollisionSystem extends System       // Detect collisions
public class AnimationSystem extends System       // Update animations
```

## Graphics

### RenderEngine

Core rendering system with OpenGL integration.

```java
public class RenderEngine {
    public void initialize()                       // Setup OpenGL context
    public void beginFrame()                       // Start frame rendering
    public void endFrame()                         // Finish and present frame
    public void drawSprite(Sprite sprite, float x, float y)  // Draw single sprite
    public void drawTile(Tile tile, int x, int y)  // Draw map tile
    public Camera getCamera()                      // Get current camera
}
```

### Camera

Viewport management for world rendering.

```java
public class Camera {
    public void setPosition(float x, float y)      // Set camera position
    public void follow(Entity target)              // Follow entity smoothly
    public void setZoom(float zoom)                // Set zoom level
    public Vector2f worldToScreen(float x, float y) // Convert coordinates
    public Vector2f screenToWorld(float x, float y) // Convert coordinates
}
```

### Sprite

Texture and animation data.

```java
public class Sprite {
    public void load(String path)                  // Load texture from file
    public void setFrame(int frame)                // Set animation frame
    public int getWidth()                          // Get sprite width
    public int getHeight()                         // Get sprite height
    public void draw(float x, float y)             // Draw at position
}
```

## Input

### InputManager

Handle keyboard and mouse input.

```java
public class InputManager {
    public boolean isKeyPressed(int keyCode)       // Check key state
    public boolean isKeyJustPressed(int keyCode)   // Check key press event
    public Vector2f getMousePosition()             // Get mouse coordinates
    public boolean isMouseButtonPressed(int button) // Check mouse button
    public void update()                           // Update input state
}
```

### Input Constants

```java
public class Keys {
    public static final int W = GLFW_KEY_W;
    public static final int A = GLFW_KEY_A;
    public static final int S = GLFW_KEY_S;
    public static final int D = GLFW_KEY_D;
    public static final int SPACE = GLFW_KEY_SPACE;
    public static final int ENTER = GLFW_KEY_ENTER;
    public static final int ESCAPE = GLFW_KEY_ESCAPE;
}
```

## Audio

### AudioManager

Sound and music playback system.

```java
public class AudioManager {
    public void initialize()                       // Setup audio system
    public void playSound(String soundId)          // Play sound effect
    public void playMusic(String musicId)          // Play background music
    public void stopMusic()                        // Stop current music
    public void setMasterVolume(float volume)      // Set global volume
    public void setSoundVolume(float volume)       // Set SFX volume
    public void setMusicVolume(float volume)       // Set music volume
}
```

## Game States

### GameState Interface

Base interface for all game states.

```java
public sealed interface GameState 
    permits MenuState, WorldState, BattleState, DialogueState {
    
    void enter();                                  // Called when state becomes active
    void exit();                                   // Called when state is removed
    void update(float deltaTime);                  // Update game logic
    void render(RenderEngine renderer);            // Draw state content
    void handleInput(InputManager input);          // Process user input
}
```

### State Implementations

```java
public final class WorldState implements GameState {
    // Overworld exploration, NPC interaction, map transitions
}

public final class BattleState implements GameState {
    // Turn-based combat system
}

public final class DialogueState implements GameState {
    // NPC conversations and text display
}

public final class MenuState implements GameState {
    // Main menu, pause menu, inventory screens
}
```

## Utilities

### Vector2f

2D vector math operations.

```java
public record Vector2f(float x, float y) {
    public Vector2f add(Vector2f other)            // Vector addition
    public Vector2f subtract(Vector2f other)       // Vector subtraction
    public Vector2f multiply(float scalar)         // Scalar multiplication
    public float length()                          // Vector magnitude
    public Vector2f normalize()                    // Unit vector
    public float dot(Vector2f other)               // Dot product
}
```

### MathUtils

Common mathematical operations.

```java
public class MathUtils {
    public static float lerp(float a, float b, float t)     // Linear interpolation
    public static float clamp(float value, float min, float max) // Constrain value
    public static float distance(float x1, float y1, float x2, float y2) // Distance
    public static boolean intersects(Rectangle a, Rectangle b) // Collision test
}
```

## Events

### EventBus

Decoupled communication system.

```java
public class EventBus {
    public void subscribe(Class<? extends Event> eventType, EventListener listener)
    public void unsubscribe(Class<? extends Event> eventType, EventListener listener)
    public void publish(Event event)              // Send event to subscribers
}
```

### Common Events

```java
public record EntityCreatedEvent(Entity entity) implements Event {}
public record EntityDestroyedEvent(Entity entity) implements Event {}
public record CollisionEvent(Entity a, Entity b) implements Event {}
public record StateChangeEvent(GameState oldState, GameState newState) implements Event {}
```
