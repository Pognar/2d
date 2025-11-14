package engine.core;

import game.states.BedroomDemoState;
import engine.input.InputManager;

public class GameEngine {
    private final StateManager stateManager = new StateManager();
    private final InputManager inputManager = new InputManager();
    private boolean running = false;
    
    public void run() {
        System.out.println("🚀 2D RPG Engine Starting - Bedroom Awakening Demo!");
        init();
        loop();
        cleanup();
    }
    
    private void init() {
        System.out.println("⚙️  Engine initialized");
        
        inputManager.init();
        
        // Start with bedroom demo state
        stateManager.pushState(new BedroomDemoState(inputManager));
        
        running = true;
    }
    
    private void loop() {
        while (running) {
            float deltaTime = 0.016f; // ~60 FPS
            
            inputManager.update();
            
            // Check for ESC key to quit
            if (inputManager.isKeyPressed(27)) {
                System.out.println("👋 ESC pressed - Quitting game");
                running = false;
                break;
            }
            
            stateManager.update(deltaTime);
            stateManager.render();
            
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    private void cleanup() {
        System.out.println("🏁 Engine shutdown - Thanks for playing!");
    }
    
    public static void main(String[] args) {
        new GameEngine().run();
    }
}
