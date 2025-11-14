package engine.graphics;

import engine.ecs.Entity;
import engine.ecs.PositionComponent;
import engine.ecs.RenderComponent;
import engine.input.InputManager;

import java.util.List;

public class RenderEngine {
    private GameWindow window;
    
    public RenderEngine(InputManager inputManager) {
        this.window = new GameWindow(inputManager);
    }
    
    public void renderTileMap(TileMap tileMap, Camera camera) {
        int startX = Math.max(0, (int) (camera.getX() / tileMap.getTileSize()));
        int startY = Math.max(0, (int) (camera.getY() / tileMap.getTileSize()));
        int endX = Math.min(tileMap.getWidth(), startX + camera.getViewWidth() / tileMap.getTileSize() + 2);
        int endY = Math.min(tileMap.getHeight(), startY + camera.getViewHeight() / tileMap.getTileSize() + 2);
        
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                Tile tile = tileMap.getTile(x, y);
                float screenX = x * tileMap.getTileSize() - camera.getX();
                float screenY = y * tileMap.getTileSize() - camera.getY();
                
                window.drawTile(tile.texturePath(), screenX, screenY, tileMap.getTileSize());
            }
        }
    }
    
    public void render(List<Entity> entities, Camera camera) {
        for (Entity entity : entities) {
            if (entity.hasComponent(PositionComponent.class) && 
                entity.hasComponent(RenderComponent.class)) {
                
                var pos = entity.getComponent(PositionComponent.class);
                var render = entity.getComponent(RenderComponent.class);
                
                float screenX = pos.x() - camera.getX();
                float screenY = pos.y() - camera.getY();
                
                // Only render if on screen
                if (screenX > -render.width() && screenX < camera.getViewWidth() &&
                    screenY > -render.height() && screenY < camera.getViewHeight()) {
                    
                    window.drawEntity(render.texturePath(), screenX, screenY, render.width(), render.height());
                }
            }
        }
    }
    
    public void clear() {
        window.clear();
    }
    
    public void present() {
        window.present();
    }
    
    public void setProjection(int width, int height) {
        // Window size is fixed, but we can log this
        System.out.printf("Setting projection to %dx%d%n", width, height);
    }
}
