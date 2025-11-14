package engine.graphics;

public record Tile(int id, boolean solid, String texturePath) {
    
    public static final Tile GRASS = new Tile(0, false, "grass.png");
    public static final Tile STONE = new Tile(1, true, "stone.png");
    public static final Tile WATER = new Tile(2, true, "water.png");
    public static final Tile TREE = new Tile(3, true, "tree.png");
}
