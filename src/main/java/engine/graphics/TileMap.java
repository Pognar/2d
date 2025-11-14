package engine.graphics;

public class TileMap {
    private final int width, height;
    private final int tileSize;
    private final Tile[][] tiles;
    
    public TileMap(int width, int height, int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.tiles = new Tile[height][width];
        
        // Initialize with grass
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[y][x] = Tile.GRASS;
            }
        }
    }
    
    public void setTile(int x, int y, Tile tile) {
        if (isValidPosition(x, y)) {
            tiles[y][x] = tile;
        }
    }
    
    public Tile getTile(int x, int y) {
        return isValidPosition(x, y) ? tiles[y][x] : Tile.STONE;
    }
    
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    public boolean isSolid(int x, int y) {
        return getTile(x, y).solid();
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public int getTileSize() { return tileSize; }
}
