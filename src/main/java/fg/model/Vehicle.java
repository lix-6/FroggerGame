package fg.model;

import javafx.geometry.Rectangle2D;

public class Vehicle implements Movable{
    private double x, y;
    private double speed;
    private boolean movingRight;
    private int level;
    public VehicleType type; //Use numbers to denote different kinds of vehicles

    public Vehicle(double startX, double startY, double speed, boolean movingRight, int level, VehicleType type) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.movingRight = movingRight;
        this.level = level;
        this.type = type;
    }

    @Override
    public void move() {
        if (movingRight) {
            x += speed;
            if (x > FroggerGame.SCREEN_WIDTH + 100) {
                x = -100;
            }
        } else {
            x -= speed;
            if (x < -100) {
                x = FroggerGame.SCREEN_WIDTH + 100;
            }
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
    public boolean isMovingRight() {
        return movingRight;
    }
    public VehicleType getType() {
        return type;
    }

    public int getVehicleWidth() {
        return type.getWidth();
    }
    public int getVehicleHeight() {
        return type.getHeight();
    }
}