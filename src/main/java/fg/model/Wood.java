package fg.model;

import javafx.geometry.Rectangle2D;

public class Wood implements Movable{
    private double x, y;
    private double speed;
    private int level;
    public WoodType type;

    public Wood(double startX, double startY, double speed, int level, WoodType type) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.level = level;
        this.type = type;
    }

    @Override
    public void move() {
        x += speed;
        if (x > FroggerGame.SCREEN_WIDTH + 150) {
            x = -150;
        } else if (x < -150) {
            x = FroggerGame.SCREEN_WIDTH + 150;
        }
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(x - type.getWidth()/2, y - type.getHeight()/2, type.getWidth(), type.getHeight());
    }

    // Getter
    @Override
    public double getX() {
        return x;
    }
    @Override
    public double getY() {
        return y;
    }
    @Override
    public double getSpeed() {
        return speed;
    }
    public WoodType getType() {
        return type;
    }
}