package engine.graphics;

import engine.input.InputManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class GameWindow extends JPanel implements KeyListener {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    private JFrame frame;
    private BufferedImage backBuffer;
    private Graphics2D g2d;
    private Map<String, Color> tileColors;
    private InputManager inputManager;
    
    public GameWindow(InputManager inputManager) {
        this.inputManager = inputManager;
        initializeWindow();
        initializeTileColors();
        backBuffer = new BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB);
        g2d = backBuffer.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
    }
    
    private void initializeWindow() {
        frame = new JFrame("2D RPG Engine - Bedroom Awakening Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(this);
        
        frame.add(this);
        frame.setVisible(true);
        this.requestFocus();
    }
    
    private void initializeTileColors() {
        tileColors = new HashMap<>();
        tileColors.put("stone.png", new Color(128, 128, 128));
        tileColors.put("grass.png", new Color(34, 139, 34));
        tileColors.put("tree.png", new Color(139, 69, 19));
        tileColors.put("water.png", new Color(30, 144, 255));
        tileColors.put("player_down.png", new Color(255, 255, 0));
        tileColors.put("teddy_bear.png", new Color(139, 69, 19));
    }
    
    public void clear() {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }
    
    public void drawTile(String texture, float x, float y, int size) {
        Color color = tileColors.getOrDefault(texture, Color.MAGENTA);
        g2d.setColor(color);
        g2d.fillRect((int)x, (int)y, size, size);
        
        g2d.setColor(Color.DARK_GRAY);
        g2d.drawRect((int)x, (int)y, size, size);
    }
    
    public void drawEntity(String texture, float x, float y, float width, float height) {
        Color color = tileColors.getOrDefault(texture, Color.RED);
        g2d.setColor(color);
        g2d.fillRect((int)x, (int)y, (int)width, (int)height);
        
        g2d.setColor(Color.BLACK);
        g2d.drawRect((int)x, (int)y, (int)width, (int)height);
    }
    
    public void present() {
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backBuffer, 0, 0, null);
    }
    
    public int getWidth() { return WINDOW_WIDTH; }
    public int getHeight() { return WINDOW_HEIGHT; }
    
    @Override
    public void keyPressed(KeyEvent e) {
        inputManager.setKeyPressed(e.getKeyCode(), true);
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        inputManager.setKeyPressed(e.getKeyCode(), false);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}
