package game.systems;

import engine.ecs.*;

import java.util.List;
import java.util.Random;

public class AISystem implements GameSystem {
    private final Random random = new Random();
    
    @Override
    public void update(List<Entity> entities, float deltaTime) {
        for (Entity entity : entities) {
            if (entity.hasComponent(AIComponent.class) && 
                entity.hasComponent(PositionComponent.class)) {
                
                var ai = entity.getComponent(AIComponent.class);
                var pos = entity.getComponent(PositionComponent.class);
                
                switch (ai.behavior()) {
                    case WANDER -> updateWander(entity, ai, pos, deltaTime);
                    case IDLE -> updateIdle(entity, ai, deltaTime);
                }
            }
        }
    }
    
    private void updateWander(Entity entity, AIComponent ai, PositionComponent pos, float deltaTime) {
        float newTimer = ai.timer() + deltaTime;
        
        if (newTimer >= 2.0f) { // Change direction every 2 seconds
            float newTargetX = pos.x() + (random.nextFloat() - 0.5f) * 100;
            float newTargetY = pos.y() + (random.nextFloat() - 0.5f) * 100;
            
            entity.addComponent(new AIComponent(ai.behavior(), 0f, newTargetX, newTargetY));
        } else {
            // Move towards target
            float dx = ai.targetX() - pos.x();
            float dy = ai.targetY() - pos.y();
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            
            if (distance > 5f) {
                float speed = 50f * deltaTime;
                float newX = pos.x() + (dx / distance) * speed;
                float newY = pos.y() + (dy / distance) * speed;
                
                entity.addComponent(new PositionComponent(newX, newY));
            }
            
            entity.addComponent(new AIComponent(ai.behavior(), newTimer, ai.targetX(), ai.targetY()));
        }
    }
    
    private void updateIdle(Entity entity, AIComponent ai, float deltaTime) {
        // Just update timer for idle NPCs
        entity.addComponent(new AIComponent(ai.behavior(), ai.timer() + deltaTime, ai.targetX(), ai.targetY()));
    }
}
