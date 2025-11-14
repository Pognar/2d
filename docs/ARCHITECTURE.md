# 2D RPG Engine - Architecture

## Overview

The engine follows a modern Entity Component System (ECS) architecture built with Java 21 features.

## Core Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                     Game Engine                             │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │ State Mgr   │  │ Input Mgr   │  │   Resource Mgr      │  │
│  │             │  │             │  │                     │  │
│  │ • Push/Pop  │  │ • WASD      │  │ • Asset Loading     │  │
│  │ • Update    │  │ • Mouse     │  │ • Caching           │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                    Game State                               │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │   World     │  │   Camera    │  │    Render Engine    │  │
│  │             │  │             │  │                     │  │
│  │ • TileMap   │  │ • Following │  │ • Tile Rendering    │  │
│  │ • Entities  │  │ • Viewport  │  │ • Entity Rendering  │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                Entity Component System                      │
├─────────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────────────┐  │
│  │  Entities   │  │ Components  │  │      Systems        │  │
│  │             │  │             │  │                     │  │
│  │ • Player    │  │ • Position  │  │ • Movement          │  │
│  │ • NPCs      │  │ • Render    │  │ • Animation         │  │
│  │ • Items     │  │ • Input     │  │ • AI                │  │
│  │             │  │ • Animation │  │                     │  │
│  │             │  │ • AI        │  │                     │  │
│  └─────────────┘  └─────────────┘  └─────────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

## Entity Component System

### Entities
- **Lightweight containers** with unique IDs
- **Component storage** via ConcurrentHashMap
- **Type-safe component access** with generics

```java
public record Entity(int id, Map<Class<? extends Component>, Component> components)
```

### Components
- **Pure data structures** using Java records
- **Sealed interface** for type safety
- **Immutable by design** for thread safety

```java
public sealed interface Component permits 
    PositionComponent, RenderComponent, InputComponent, 
    AnimationComponent, AIComponent
```

### Systems
- **Behavior processors** that operate on entities
- **Component filtering** for performance
- **Delta time updates** for frame-rate independence

```java
public interface GameSystem {
    void update(List<Entity> entities, float deltaTime);
}
```

## State Management

### Game States
- **Stack-based state management**
- **Enter/Update/Render/Exit lifecycle**
- **State transitions** via push/pop operations

### State Flow
```
MenuState → WorldState → BattleState
    ↑           ↓           ↓
    └───────────┴───────────┘
```

## Graphics Pipeline

### Rendering Order
1. **Clear screen** (background color)
2. **Render tile map** (world background)
3. **Render entities** (sprites, characters)
4. **Render UI** (menus, HUD)

### Camera System
- **Viewport management** (800x600 default)
- **Smooth following** with interpolation
- **Culling optimization** (only render visible)

### Tile System
- **Grid-based world** representation
- **Collision detection** via tile properties
- **Efficient rendering** with camera culling

## Performance Optimizations

### Rendering
- **Frustum culling**: Only render visible tiles/entities
- **Component filtering**: Systems only process relevant entities
- **Batch rendering**: Group similar render calls

### Memory Management
- **Record types**: Minimal memory overhead
- **Component reuse**: Immutable components reduce GC pressure
- **Entity pooling**: Reuse entity IDs when possible

### Threading
- **Single-threaded game loop**: Avoids synchronization overhead
- **Async resource loading**: Background asset loading (future)

## Data Flow

```
Input → Systems → Components → Entities → Rendering
  ↑                                          ↓
  └──────────── Game Loop ←──────────────────┘
```

1. **Input collection**: Keyboard/mouse state
2. **System updates**: Process entities with relevant components
3. **Component updates**: Modify entity state
4. **Rendering**: Draw current world state
5. **Repeat**: Next frame

## Design Patterns

### Used Patterns
- **Entity Component System**: Core architecture
- **State Pattern**: Game state management
- **Observer Pattern**: Input event handling
- **Factory Pattern**: Entity creation (future)
- **Command Pattern**: Input actions (future)

### Java 21 Features
- **Records**: Immutable data structures
- **Sealed Classes**: Controlled inheritance
- **Pattern Matching**: Type-safe casting
- **Text Blocks**: Multi-line strings (future)

## Extensibility

### Adding Components
1. Create record implementing `Component`
2. Add to sealed interface permits list
3. Create system to process component

### Adding Systems
1. Implement `GameSystem` interface
2. Filter entities by required components
3. Add to game state update loop

### Adding Game States
1. Implement `GameState` interface
2. Handle enter/update/render/exit lifecycle
3. Push onto state manager stack

## Dependencies

### Internal Dependencies
```
engine.core → engine.ecs
engine.graphics → engine.ecs
game.systems → engine.ecs
game.states → engine.core
```

### External Dependencies
- **Java 21 Standard Library**: Collections, I/O, etc.
- **No external libraries**: Self-contained engine

## Build Architecture

### Compilation
- **javac**: Direct Java compilation
- **No build tools**: Simple bash script
- **Classpath management**: Single target directory

### Packaging
- **Single JAR**: All classes in one archive (future)
- **Resource embedding**: Assets in classpath (future)
- **Native packaging**: Platform-specific builds (future)
