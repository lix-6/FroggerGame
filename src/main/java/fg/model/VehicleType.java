package fg.model;
public enum VehicleType {
    CAR1(50, 40, 3, "WHITE"),
    CAR2(50, 40, 3, "YELLOW"),
    CAR3(50, 40, 5, "LIGHTPINK");

    private final int width;
    private final int height;
    private final int gridSize;
    private final String color;

    private VehicleType(int width, int height, int gridSize, String color) {
        this.width = width;
        this.height = height;
        this.gridSize = gridSize;
        this.color = color;
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

    public String getColor() {
        return color;
    }
}