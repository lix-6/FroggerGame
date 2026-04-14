package fg.model;

public enum WoodType {
    SHORT(120,30,5),//Short wood
    LONG(180,30,5);//Long wood

    private final int width;
    private final int height;
    private final int gridSize;

    private WoodType(int width, int height, int gridSize) {
        this.width = width;
        this.height = height;
        this.gridSize = gridSize;
    }

    // Getter method
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getGridSize() {
        return gridSize;
    }
}
