# 2D RPG Engine - API Reference

## Core Classes

### Entity
```java
public record Entity(int id, Map<Class<? extends Component>, Component> components)
```

**Methods:**
- `addComponent(T component)` - Add component to entity
- `getComponent(Class<T> type)` - Get component by type
- `hasComponent(Class<? extends Component> type)` - Check if component exists

### GameState Interface
```java
public interface GameState {
    void enter();           // Called when state starts
    void update(float deltaTime); // Called every frame
    void render();          // Called for rendering
    void exit();            // Called when state ends
}
```

### StateManager
```java
public class StateManager
```

**Methods:**
- `pushState(GameState state)` - Add new state to stack
- `popState()` - Remove current state
- `update(float deltaTime)` - Update current state
- `render()` - Render current state

## Components

### PositionComponent
```java
public record PositionComponent(float x, float y) implements Component
```

### RenderComponent  
```java
public record RenderComponent(String texturePath, int width, int height) implements Component
```

### InputComponent
```java
public record InputComponent(boolean controllable) implements Component
```

### AnimationComponent
```java
public record AnimationComponent(
    String[] frames,     // Animation frame textures
    float frameTime,     // Time per frame in seconds
    boolean loop,        // Whether to loop animation
    float currentTime,   // Current frame timer
    int currentFrame     // Current frame index
) implements Component
```

**Methods:**
- `AnimationComponent(String[] frames, float frameTime, boolean loop)` - Constructor
- `nextFrame(float deltaTime)` - Advance animation
- `getCurrentFrame()` - Get current texture

### AIComponent
```java
public record AIComponent(
    AIBehavior behavior,
    float timer,
    float targetX,
    float targetY
) implements Component
```

**Behaviors:**
- `AIBehavior.IDLE` - Stay in place
- `AIBehavior.WANDER` - Move randomly
- `AIBehavior.PATROL` - Move between points (future)
- `AIBehavior.FOLLOW_PLAYER` - Follow player (future)

## Graphics System

### TileMap
```java
public class TileMap
```

**Constructor:**
- `TileMap(int width, int height, int tileSize)`

**Methods:**
- `setTile(int x, int y, Tile tile)` - Place tile at position
- `getTile(int x, int y)` - Get tile at position
- `isSolid(int x, int y)` - Check if tile blocks movement
- `isValidPosition(int x, int y)` - Check bounds

### Tile
```java
public record Tile(int id, boolean solid, String texturePath)
```

**Predefined Tiles:**
- `Tile.GRASS` - Walkable ground (id: 0)
- `Tile.STONE` - Solid wall (id: 1)  
- `Tile.WATER` - Solid liquid (id: 2)
- `Tile.TREE` - Solid obstacle (id: 3)

### Camera
```java
public class Camera
```

**Constructor:**
- `Camera(int viewWidth, int viewHeight)`

**Methods:**
- `setTarget(Entity target)` - Set entity to follow
- `update()` - Update camera position (smooth following)
- `getX()`, `getY()` - Get camera position
- `getViewWidth()`, `getViewHeight()` - Get viewport size

### RenderEngine
```java
public class RenderEngine
```

**Methods:**
- `render(List<Entity> entities, Camera camera)` - Render entities
- `renderTileMap(TileMap tileMap, Camera camera)` - Render world tiles
- `setProjection(int width, int height)` - Set screen dimensions

## Game Systems

### MovementSystem
```java
public class MovementSystem implements GameSystem
```

**Constructor:**
- `MovementSystem(InputManager inputManager, TileMap tileMap)`

**Features:**
- WASD movement (W=87, A=65, S=83, D=68)
- Collision detection with solid tiles
- 100 pixels/second movement speed

### AnimationSystem
```java
public class AnimationSystem implements GameSystem
```

**Features:**
- Automatic frame advancement
- Updates RenderComponent texture
- Supports looping and one-shot animations

### AISystem
```java
public class AISystem implements GameSystem
```

**Features:**
- WANDER: Random movement every 2 seconds
- IDLE: No movement, timer updates only
- 50 pixels/second NPC movement speed

## Input System

### InputManager
```java
public class InputManager
```

**Methods:**
- `init()` - Initialize input system
- `isKeyPressed(int key)` - Check if key is pressed
- `isMouseButtonPressed(int button)` - Check mouse button
- `getMouseX()`, `getMouseY()` - Get mouse position

**Key Codes:**
- W: 87, A: 65, S: 83, D: 68
- Space: 32, Enter: 10, Escape: 27

## Resource Management

### ResourceManager
```java
public class ResourceManager
```

**Methods:**
- `loadResource(String path)` - Load resource from classpath
- `storeResource(String key, Object resource)` - Cache resource
- `getResource(String key, Class<T> type)` - Retrieve cached resource
- `hasResource(String key)` - Check if resource exists

## Constants

### Performance Settings
- **Tile Size**: 32x32 pixels (recommended)
- **Frame Rate**: 60 FPS (16ms per frame)
- **Movement Speed**: 100 pixels/second (player), 50 pixels/second (NPCs)
- **Animation Speed**: 0.2 seconds per frame (recommended)

### World Limits
- **Max Map Size**: Limited by memory (tested up to 100x100)
- **Max Entities**: Limited by performance (tested up to 1000)
- **Tile Types**: 4 built-in, extensible

## Error Handling

### Common Issues
- **NullPointerException**: Entity missing required component
- **ArrayIndexOutOfBounds**: Invalid tile coordinates
- **ClassCastException**: Wrong component type requested

### Best Practices
- Always check `hasComponent()` before `getComponent()`
- Validate tile coordinates with `isValidPosition()`
- Use try-catch for resource loading
- Check entity lists for null before iteration
