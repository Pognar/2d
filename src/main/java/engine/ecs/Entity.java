package engine.ecs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public record Entity(int id, Map<Class<? extends Component>, Component> components) {
    
    public Entity(int id) {
        this(id, new ConcurrentHashMap<>());
    }
    
    public <T extends Component> void addComponent(T component) {
        components.put(component.getClass(), component);
    }
    
    @SuppressWarnings("unchecked")
    public <T extends Component> T getComponent(Class<T> type) {
        return (T) components.get(type);
    }
    
    public boolean hasComponent(Class<? extends Component> type) {
        return components.containsKey(type);
    }
}
