package engine.ecs;

public record RenderComponent(String texturePath, int width, int height) implements Component {}
