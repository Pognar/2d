package game.systems;

import engine.ecs.*;

import java.util.List;

public class AnimationSystem implements GameSystem {
    
    @Override
    public void update(List<Entity> entities, float deltaTime) {
        for (Entity entity : entities) {
            if (entity.hasComponent(AnimationComponent.class)) {
                var animation = entity.getComponent(AnimationComponent.class);
                var newAnimation = animation.nextFrame(deltaTime);
                
                entity.addComponent(newAnimation);
                
                // Update render component with current frame
                if (entity.hasComponent(RenderComponent.class)) {
                    var render = entity.getComponent(RenderComponent.class);
                    var newRender = new RenderComponent(
                        newAnimation.getCurrentFrame(),
                        render.width(),
                        render.height()
                    );
                    entity.addComponent(newRender);
                }
            }
        }
    }
}
