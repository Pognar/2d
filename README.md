# 2D Top-Down RPG Engine

A Java 21-based game engine for creating 2D top-down RPG games similar to EarthBound and Game Boy Pokemon.

## 🚀 Quick Start

```bash
# Build the engine
./build.sh

# Run the Bedroom Awakening Demo
java -cp target/classes engine.core.GameEngine
```

**🎮 Controls:**
- **WASD** - Move player around the bedroom
- **SPACE** - Interact with teddy bear when close
- **ESC** - Quit game

## ✨ Features

- **Modern Java 21**: Records, sealed classes, pattern matching
- **Entity Component System**: Flexible ECS architecture
- **Visual Graphics**: 800x600 game window with tile-based rendering
- **Tile-Based World**: Efficient rendering with collision detection
- **Camera System**: Smooth following camera
- **Interactive Objects**: SPACE key interactions with game objects
- **Real-time Input**: WASD movement with immediate response
- **No Dependencies**: Self-contained, pure Java with Swing graphics

## 📋 Requirements

- **Java 21+** (required)
- **No external libraries** needed

## 🎮 Demo Features

The "Bedroom Awakening" demo showcases:
- **Visual 10x8 bedroom** with stone walls, grass floor, bed, and window
- **Player character** (yellow square) with WASD movement
- **Interactive teddy bear** - get close and press SPACE to interact
- **Smooth camera** following player movement
- **Collision detection** with walls and obstacles
- **Real-time graphics** at 60 FPS

## 📚 Documentation

- **[Developer Guide](DEVELOPER_GUIDE.md)** - How to create games
- **[API Reference](API_REFERENCE.md)** - Complete API documentation
- **[Architecture Overview](docs/ARCHITECTURE.md)** - Engine design
- **[Development Guide](docs/DEVELOPMENT.md)** - Contributing

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Game States   │    │   ECS Systems   │    │   Components    │
│                 │    │                 │    │                 │
│ • WorldState    │───▶│ • MovementSys   │───▶│ • Position      │
│ • MenuState     │    │ • AnimationSys  │    │ • Render        │
│ • BattleState   │    │ • AISys         │    │ • Input         │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         ▼                       ▼                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│  State Manager  │    │  Render Engine  │    │   Entities      │
│                 │    │                 │    │                 │
│ • Push/Pop      │───▶│ • Tile Render   │───▶│ • Player        │
│ • Update/Render │    │ • Entity Render │    │ • NPCs          │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

## 🎯 Example Usage

```java
// Create a game world
TileMap world = new TileMap(20, 20, 32);
world.setTile(5, 5, Tile.STONE);

// Create player
Entity player = new Entity(1);
player.addComponent(new PositionComponent(100, 100));
player.addComponent(new RenderComponent("hero.png", 32, 32));
player.addComponent(new InputComponent(true));

// Add animations
String[] walkFrames = {"walk1.png", "walk2.png", "walk3.png"};
player.addComponent(new AnimationComponent(walkFrames, 0.2f, true));

// Create NPC
Entity npc = new Entity(2);
npc.addComponent(new PositionComponent(200, 200));
npc.addComponent(new RenderComponent("villager.png", 32, 32));
npc.addComponent(new AIComponent(AIComponent.AIBehavior.WANDER));
```

## 🛠️ Build System

The engine uses a simple build script that compiles all Java files:

```bash
#!/bin/bash
mkdir -p target/classes
cd src/main/java
javac -d ../../../target/classes $(find . -name "*.java")
```

## 📁 Project Structure

```
src/
├── engine/          # Core engine components
│   ├── core/        # Game loop, state management
│   ├── graphics/    # Rendering, tiles, camera
│   ├── input/       # Input handling
│   └── ecs/         # Entity Component System
├── game/            # Game implementations
│   ├── systems/     # Game logic systems
│   └── states/      # Game state implementations
└── resources/       # Game assets (future)
    ├── sprites/     # Sprite sheets
    ├── maps/        # World data
    └── audio/       # Sound files
```

## 🎮 Controls

| Key | Action |
|-----|--------|
| W | Move up |
| A | Move left |
| S | Move down |
| D | Move right |
| SPACE | Interact with objects |
| ESC | Quit game |

## 🚧 Roadmap

### ✅ Phase 1: Foundation (Complete)
- ECS architecture
- Game loop and state management
- Basic rendering system

### ✅ Phase 2: World & Graphics (Complete)
- Tile-based world system
- Camera system
- Animation system
- Player movement
- NPC AI system

### 🔄 Phase 3: Game Features (Next)
- Dialogue system
- Turn-based combat
- Inventory system
- Save/load system

### 📋 Phase 4: Polish & UI
- Menu systems
- UI components
- Performance optimizations
- Developer tools

## 🤝 Contributing

1. Read the [Development Guide](docs/DEVELOPMENT.md)
2. Check the [TODO List](TODO.md)
3. Follow Java 21 best practices
4. Ensure all code builds with `./build.sh`

## 📄 License

MIT License - See [LICENSE](LICENSE) file for details

## 🎯 Goals

This engine aims to provide a solid foundation for creating classic 2D RPG games with modern Java features, focusing on:

- **Simplicity**: Easy to understand and extend
- **Performance**: Efficient rendering and game logic
- **Flexibility**: Component-based architecture
- **Modern**: Leveraging Java 21 features
