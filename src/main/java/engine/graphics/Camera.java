package engine.graphics;

import engine.ecs.Entity;
import engine.ecs.PositionComponent;

public class Camera {
    private float x, y;
    private final int viewWidth, viewHeight;
    private Entity target;
    
    public Camera(int viewWidth, int viewHeight) {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
    }
    
    public void setTarget(Entity target) {
        this.target = target;
    }
    
    public void update() {
        if (target != null && target.hasComponent(PositionComponent.class)) {
            var pos = target.getComponent(PositionComponent.class);
            
            // Smooth following
            float targetX = pos.x() - viewWidth / 2f;
            float targetY = pos.y() - viewHeight / 2f;
            
            x += (targetX - x) * 0.1f;
            y += (targetY - y) * 0.1f;
        }
    }
    
    public float getX() { return x; }
    public float getY() { return y; }
    public int getViewWidth() { return viewWidth; }
    public int getViewHeight() { return viewHeight; }
}
