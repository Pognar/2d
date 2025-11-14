package engine.core;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private final Map<String, Object> resources = new HashMap<>();
    
    public InputStream loadResource(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getResource(String key, Class<T> type) {
        return (T) resources.get(key);
    }
    
    public void storeResource(String key, Object resource) {
        resources.put(key, resource);
    }
    
    public boolean hasResource(String key) {
        return resources.containsKey(key);
    }
}
