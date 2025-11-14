package engine.ecs;

public record AIComponent(
    AIBehavior behavior,
    float timer,
    float targetX,
    float targetY
) implements Component {
    
    public enum AIBehavior {
        IDLE,
        WANDER,
        PATROL,
        FOLLOW_PLAYER
    }
    
    public AIComponent(AIBehavior behavior) {
        this(behavior, 0f, 0f, 0f);
    }
}
