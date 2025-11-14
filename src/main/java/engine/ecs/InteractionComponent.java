package engine.ecs;

public record InteractionComponent(
    String message,
    boolean canInteract
) implements Component {
    
    public InteractionComponent(String message) {
        this(message, true);
    }
}
