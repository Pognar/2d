package engine.ecs;

import java.util.List;

public interface GameSystem {
    void update(List<Entity> entities, float deltaTime);
}
