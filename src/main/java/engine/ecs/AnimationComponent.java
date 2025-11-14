package engine.ecs;

public record AnimationComponent(
    String[] frames,
    float frameTime,
    boolean loop,
    float currentTime,
    int currentFrame
) implements Component {
    
    public AnimationComponent(String[] frames, float frameTime, boolean loop) {
        this(frames, frameTime, loop, 0f, 0);
    }
    
    public AnimationComponent nextFrame(float deltaTime) {
        float newTime = currentTime + deltaTime;
        
        if (newTime >= frameTime) {
            int newFrame = currentFrame + 1;
            
            if (newFrame >= frames.length) {
                if (loop) {
                    newFrame = 0;
                } else {
                    newFrame = frames.length - 1;
                }
            }
            
            return new AnimationComponent(frames, frameTime, loop, 0f, newFrame);
        }
        
        return new AnimationComponent(frames, frameTime, loop, newTime, currentFrame);
    }
    
    public String getCurrentFrame() {
        return frames[currentFrame];
    }
}
