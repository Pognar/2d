# Demo Implementation Guide

## Implementation Overview

This guide provides the technical roadmap for implementing the "Bedroom Awakening" demo. Follow this step-by-step approach to build the minimal viable demo.

## Project Setup

### Maven Configuration
Create `pom.xml` with Java 21 and LWJGL dependencies:

```xml
<properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
    <lwjgl.version>3.3.3</lwjgl.version>
</properties>

<dependencies>
    <!-- LWJGL Core -->
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl</artifactId>
        <version>${lwjgl.version}</version>
    </dependency>
    <!-- LWJGL OpenGL -->
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl-opengl</artifactId>
        <version>${lwjgl.version}</version>
    </dependency>
    <!-- LWJGL GLFW -->
    <dependency>
        <groupId>org.lwjgl</groupId>
        <artifactId>lwjgl-glfw</artifactId>
        <version>${lwjgl.version}</version>
    </dependency>
</dependencies>
```

### Directory Structure
```
src/main/java/
├── engine/
│   ├── core/
│   │   ├── GameEngine.java
│   │   └── GameLoop.java
│   ├── graphics/
│   │   ├── Renderer.java
│   │   ├── Texture.java
│   │   └── Camera.java
│   ├── input/
│   │   └── InputManager.java
│   └── ecs/
│       ├── Entity.java
│       ├── Component.java
│       └── System.java
└── demo/
    ├── DemoGame.java
    ├── components/
    └── systems/
```

## Core Engine Components

### 1. Game Engine Foundation

**GameEngine.java** - Main engine class
```java
public class GameEngine {
    private boolean running;
    private final int TARGET_FPS = 60;
    
    public void start() {
        // Initialize GLFW, OpenGL
        // Create window
        // Start game loop
    }
    
    public void update(float deltaTime) {
        // Update all systems
    }
    
    public void render() {
        // Render all entities
    }
}
```

**Key Responsibilities:**
- Window creation and management
- Game loop with fixed timestep
- System coordination
- Resource cleanup

### 2. Entity Component System

**Entity.java** - Entity container
```java
public class Entity {
    private final int id;
    private final Map<Class<? extends Component>, Component> components;
    
    public <T extends Component> void addComponent(T component);
    public <T extends Component> T getComponent(Class<T> type);
    public boolean hasComponent(Class<? extends Component> type);
}
```

**Component Examples:**
```java
public record PositionComponent(float x, float y) implements Component {}
public record SpriteComponent(String texturePath) implements Component {}
public record InputComponent(boolean controllable) implements Component {}
public record CollisionComponent(float width, float height) implements Component {}
```

### 3. Rendering System

**Renderer.java** - OpenGL rendering
```java
public class Renderer {
    public void initialize();
    public void beginFrame();
    public void drawSprite(Texture texture, float x, float y);
    public void drawTile(Texture texture, int tileX, int tileY);
    public void endFrame();
}
```

**Texture.java** - Texture loading
```java
public class Texture {
    private int textureId;
    private int width, height;
    
    public static Texture load(String path);
    public void bind();
    public void cleanup();
}
```

### 4. Input System

**InputManager.java** - Keyboard input
```java
public class InputManager {
    private final boolean[] keys = new boolean[GLFW_KEY_LAST];
    
    public void update();
    public boolean isKeyPressed(int key);
    public boolean isKeyJustPressed(int key);
}
```

## Demo-Specific Implementation

### 1. Room Setup

**Room Data Structure:**
```java
public class Room {
    private final int width = 10;
    private final int height = 8;
    private final int[][] tiles;
    private final List<Entity> objects;
    
    // Tile types
    public static final int FLOOR = 0;
    public static final int WALL = 1;
    public static final int BED = 2;
}
```

**Room Layout Implementation:**
```java
private void createRoom() {
    // Create walls around perimeter
    // Place floor tiles in interior
    // Add bed at spawn location
    // Place teddy bear in room
}
```

### 2. Player System

**PlayerMovementSystem.java:**
```java
public class PlayerMovementSystem extends System {
    @Override
    public void update(float deltaTime) {
        for (Entity entity : getEntitiesWithComponents(
            PositionComponent.class, 
            InputComponent.class)) {
            
            handleMovementInput(entity, deltaTime);
            checkCollisions(entity);
        }
    }
}
```

**Movement Logic:**
- Check WASD input
- Calculate new position
- Validate against collision map
- Update position if valid

### 3. Interaction System

**InteractionSystem.java:**
```java
public class InteractionSystem extends System {
    @Override
    public void update(float deltaTime) {
        if (inputManager.isKeyJustPressed(GLFW_KEY_SPACE)) {
            Entity player = getPlayerEntity();
            Entity interactable = findNearbyInteractable(player);
            
            if (interactable != null) {
                showInteractionText(interactable);
            }
        }
    }
}
```

### 4. Collision Detection

**CollisionSystem.java:**
```java
public class CollisionSystem {
    public boolean canMoveTo(float x, float y) {
        int tileX = (int) (x / TILE_SIZE);
        int tileY = (int) (y / TILE_SIZE);
        
        return room.getTile(tileX, tileY) != WALL;
    }
    
    public boolean isAdjacentTo(Entity a, Entity b) {
        // Check if entities are within interaction range
    }
}
```

## Implementation Phases

### Phase 1: Basic Window and Rendering
1. Set up LWJGL window creation
2. Initialize OpenGL context
3. Implement basic sprite rendering
4. Test with placeholder colored rectangles

**Acceptance Criteria:**
- Window opens and displays
- Can render colored rectangles
- Stable 60 FPS

### Phase 2: Player Movement
1. Create player entity with position component
2. Implement input handling for WASD
3. Add movement system
4. Test movement with placeholder sprite

**Acceptance Criteria:**
- Player sprite moves with WASD keys
- Movement is smooth and responsive
- No collision detection yet

### Phase 3: Room and Collision
1. Create room tilemap
2. Implement tile rendering
3. Add collision detection
4. Test player collision with walls

**Acceptance Criteria:**
- Room displays correctly
- Player cannot walk through walls
- Collision detection is accurate

### Phase 4: Interaction
1. Place teddy bear entity
2. Implement interaction system
3. Add text display for messages
4. Test interaction with SPACE key

**Acceptance Criteria:**
- Teddy bear appears in room
- Interaction message displays when near teddy
- SPACE key triggers interaction

### Phase 5: Polish and Testing
1. Add proper sprite assets
2. Implement UI for controls
3. Add ESC key to quit
4. Performance testing and optimization

**Acceptance Criteria:**
- All sprites display correctly
- UI shows control instructions
- Demo runs smoothly for extended periods

## Testing Strategy

### Unit Testing
- Test collision detection algorithms
- Verify entity component operations
- Test input handling logic

### Integration Testing
- Test system interactions
- Verify rendering pipeline
- Test complete gameplay loop

### Performance Testing
- Monitor frame rate consistency
- Check memory usage patterns
- Profile rendering performance

## Common Implementation Pitfalls

### Performance Issues
- **Problem**: Frame rate drops with many entities
- **Solution**: Implement basic culling, limit entities in demo

### Collision Detection
- **Problem**: Player gets stuck in walls
- **Solution**: Validate movement before applying position changes

### Input Handling
- **Problem**: Key repeat causing multiple actions
- **Solution**: Track key press/release states properly

### Resource Management
- **Problem**: Texture memory leaks
- **Solution**: Proper texture cleanup on shutdown

## Debugging Tools

### Console Output
- Log entity positions
- Track system update times
- Monitor input events

### Visual Debugging
- Draw collision boundaries
- Show entity IDs
- Display performance metrics

### Development Commands
- Teleport player to positions
- Toggle collision detection
- Reload assets without restart

## Deployment

### Build Process
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="demo.DemoGame"
```

### Distribution
- Create executable JAR with dependencies
- Include asset files in resources
- Provide simple run script

### Platform Testing
- Test on Windows, macOS, Linux
- Verify OpenGL compatibility
- Check asset loading paths
