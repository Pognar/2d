# Sprite Asset Specifications

## Overview

This document defines the visual assets required for the "Bedroom Awakening" demo. All sprites should follow a consistent pixel art style reminiscent of Game Boy Pokemon and EarthBound.

## Technical Specifications

### General Requirements
- **Format**: PNG with transparency
- **Size**: 16x16 pixels per tile/sprite
- **Color Palette**: Limited to 16 colors for retro aesthetic
- **Style**: Top-down perspective, pixel art
- **Transparency**: Use alpha channel for non-solid areas

### Color Palette Suggestion
```
Primary Colors:
#2D1B00 - Dark Brown (outlines, shadows)
#8B4513 - Medium Brown (wood, teddy bear)
#DEB887 - Light Brown (skin, light wood)
#F5DEB3 - Cream (walls, light surfaces)
#228B22 - Forest Green (accents)
#4169E1 - Royal Blue (clothing, accents)
#DC143C - Crimson Red (details)
#FFD700 - Gold (highlights)
```

## Character Sprites

### Player Character
**File Names**: `player_down.png`, `player_up.png`, `player_left.png`, `player_right.png`

**Design Requirements:**
- Young protagonist (child/teen appearance)
- Simple clothing (shirt and pants/shorts)
- Distinct silhouette for easy recognition
- Consistent proportions across all directions

**Directional Views:**
- **Down**: Front-facing, shows face details
- **Up**: Back view, shows hair/clothing back
- **Left**: Profile view, facing left
- **Right**: Mirror of left view (or separate sprite)

**Animation Notes:**
- Static sprites for now (walking animation in future phases)
- Clear directional indication
- Readable at small size

## Environment Tiles

### Floor Tile
**File Name**: `floor_tile.png`
- Wooden plank pattern
- Warm brown tones
- Subtle texture without being busy
- Seamless tiling capability

### Wall Tile  
**File Name**: `wall_tile.png`
- Simple wall texture (wallpaper or paint)
- Light color (cream/beige)
- Minimal pattern or solid color
- Should contrast well with floor

### Bed Tile
**File Name**: `bed_tile.png`
- Single bed viewed from above
- Pillow at head of bed
- Blanket/sheet texture
- Positioned for player to spawn on

### Window Tile
**File Name**: `window_tile.png`
- Simple window frame
- Glass with subtle reflection
- Optional curtains or blinds
- Decorative element only (non-functional)

## Interactive Objects

### Teddy Bear
**File Name**: `teddy_bear.png`
- Classic teddy bear appearance
- Brown fur color
- Sitting position
- Friendly, approachable design
- Clear interactive object appearance

## Asset Organization

### Directory Structure
```
resources/
└── sprites/
    ├── characters/
    │   ├── player_down.png
    │   ├── player_up.png
    │   ├── player_left.png
    │   └── player_right.png
    ├── tiles/
    │   ├── floor_tile.png
    │   ├── wall_tile.png
    │   ├── bed_tile.png
    │   └── window_tile.png
    └── objects/
        └── teddy_bear.png
```

## Quality Guidelines

### Pixel Art Best Practices
- **Clean Lines**: Avoid anti-aliasing, use solid pixels
- **Consistent Light Source**: Top-left lighting for all sprites
- **Readable Silhouettes**: Sprites should be recognizable in silhouette
- **Consistent Style**: All assets should feel cohesive

### Technical Validation
- **File Size**: Each sprite should be under 1KB
- **Transparency**: Proper alpha channel usage
- **No Artifacts**: Clean edges, no compression artifacts
- **Correct Dimensions**: Exactly 16x16 pixels

## Reference Materials

### Style References
- **Game Boy Pokemon** (Red/Blue/Yellow) - Character and tile style
- **EarthBound** - Color palette and whimsical design
- **Classic 16-bit RPGs** - General aesthetic approach

### Mood and Tone
- **Cozy**: Bedroom should feel warm and comfortable
- **Nostalgic**: Retro pixel art aesthetic
- **Friendly**: Non-threatening, approachable design
- **Clear**: Easy to understand visual hierarchy

## Placeholder Assets

If final art assets are not ready, developers should use:
- **Colored Rectangles**: Simple solid colors for tiles
- **Basic Shapes**: Circle for player, square for objects
- **Text Labels**: Letter overlays for identification (P for player, T for teddy)

### Placeholder Color Coding
```
Player: Blue rectangle
Teddy Bear: Brown rectangle  
Floor: Light gray
Walls: Dark gray
Bed: Green rectangle
Window: Yellow rectangle
```

## Delivery Requirements

### Asset Delivery
- **Format**: Individual PNG files
- **Naming**: Exact file names as specified
- **Organization**: Proper directory structure
- **Documentation**: Brief description of each asset

### Review Process
1. **Technical Review**: Verify dimensions and format
2. **Style Review**: Ensure consistency and quality
3. **Integration Test**: Test in-game appearance
4. **Approval**: Final sign-off before implementation

## Future Considerations

### Animation Preparation
- Design sprites with animation in mind
- Consistent proportions for frame-by-frame animation
- Consider walk cycles for future implementation

### Scalability
- Assets should work well at different zoom levels
- Consider how sprites will look in larger environments
- Plan for additional character expressions/poses

## Contact Information

For questions about sprite specifications:
- **Technical Questions**: Development team lead
- **Art Direction**: Art director/lead artist  
- **Style Clarifications**: System architect

## Revision History

- **v1.0**: Initial sprite specifications for bedroom demo
- **Future**: Updates based on feedback and requirements changes
