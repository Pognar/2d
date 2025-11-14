# Development Guide

## Getting Started

### Prerequisites

- **Java 21+**: Download from [OpenJDK](https://openjdk.org/) or [Oracle](https://www.oracle.com/java/)
- **Maven 3.8+**: Build automation tool
- **IDE**: IntelliJ IDEA (recommended) or Eclipse with Java 21 support
- **Git**: Version control

### Environment Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd 2d-engine
   ```

2. **Verify Java version**
   ```bash
   java --version
   # Should show Java 21 or higher
   ```

3. **Build the project**
   ```bash
   mvn clean compile
   ```

4. **Run tests**
   ```bash
   mvn test
   ```

## Project Structure

```
2d-engine/
├── src/
│   ├── main/java/
│   │   ├── engine/          # Core engine code
│   │   │   ├── core/        # Game loop, state management
│   │   │   ├── ecs/         # Entity Component System
│   │   │   ├── graphics/    # Rendering, animation
│   │   │   ├── input/       # Input handling
│   │   │   └── audio/       # Audio management
│   │   └── game/            # Game-specific implementations
│   │       ├── entities/    # Game entities
│   │       ├── systems/     # Game logic systems
│   │       └── states/      # Game states
│   ├── main/resources/      # Game assets
│   │   ├── sprites/         # Textures, sprite sheets
│   │   ├── maps/            # Level data
│   │   ├── audio/           # Sound effects, music
│   │   └── data/            # Configuration files
│   └── test/java/           # Unit tests
├── docs/                    # Documentation
├── pom.xml                  # Maven configuration
└── README.md               # Project overview
```

## Coding Standards

### Java 21 Best Practices

**Use Records for Data**
```java
// Good
public record Position(float x, float y) {}

// Avoid
public class Position {
    private final float x, y;
    // constructor, getters, equals, hashCode...
}
```

**Leverage Sealed Classes**
```java
public sealed interface GameState 
    permits MenuState, WorldState, BattleState {}
```

**Pattern Matching**
```java
switch (component) {
    case PositionComponent(var x, var y) -> updatePosition(entity, x, y);
    case VelocityComponent(var dx, var dy) -> updateVelocity(entity, dx, dy);
}
```

### Code Style

- **Naming**: Use descriptive names (PascalCase for classes, camelCase for methods/variables)
- **Methods**: Keep methods small and focused (max 20 lines)
- **Comments**: Document public APIs and complex algorithms
- **Formatting**: Use consistent indentation (4 spaces)

### Performance Guidelines

**Avoid Allocations in Game Loop**
```java
// Good - reuse objects
private final Vector2f tempVector = new Vector2f();

// Bad - creates garbage
return new Vector2f(x, y);
```

**Use Object Pooling**
```java
// For frequently created/destroyed objects
ObjectPool<Bullet> bulletPool = new ObjectPool<>(Bullet::new);
```

## Testing Strategy

### Unit Tests
- Test individual components and systems
- Mock external dependencies
- Use JUnit 5 with AssertJ for assertions

### Integration Tests
- Test system interactions
- Verify game state transitions
- Test asset loading/saving

### Example Test
```java
@Test
void shouldMoveEntityWithVelocity() {
    var entity = world.createEntity();
    entity.add(new PositionComponent(0, 0));
    entity.add(new VelocityComponent(10, 5));
    
    movementSystem.update(1.0f); // 1 second
    
    var position = entity.get(PositionComponent.class);
    assertThat(position.x()).isEqualTo(10);
    assertThat(position.y()).isEqualTo(5);
}
```

## Debugging

### Logging
Use Java's built-in logging:
```java
private static final Logger logger = Logger.getLogger(GameEngine.class.getName());
logger.info("Game started");
logger.warning("Low memory detected");
```

### Debug Tools
- **Entity Inspector**: View entity components at runtime
- **Performance Profiler**: Monitor FPS, memory usage
- **Console Commands**: Spawn entities, change game state

## Build & Deployment

### Maven Profiles

**Development**
```bash
mvn compile exec:java -Dexec.mainClass="engine.core.GameEngine"
```

**Release**
```bash
mvn clean package -Prelease
```

### Creating Distributions
```bash
# Create executable JAR with dependencies
mvn clean package assembly:single
```

## Contributing

### Workflow
1. Create feature branch from `main`
2. Implement changes with tests
3. Run full test suite
4. Submit pull request
5. Code review and merge

### Commit Messages
```
feat: add sprite animation system
fix: resolve memory leak in audio manager
docs: update API documentation
test: add unit tests for collision detection
```

### Pull Request Checklist
- [ ] Code follows style guidelines
- [ ] Tests pass locally
- [ ] Documentation updated
- [ ] No breaking changes (or documented)
- [ ] Performance impact considered
