package engine.ecs;

public sealed interface Component permits 
    PositionComponent,
    RenderComponent,
    InputComponent,
    AnimationComponent,
    AIComponent,
    InteractionComponent {}
