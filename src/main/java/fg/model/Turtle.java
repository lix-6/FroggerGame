package fg.model;

import javafx.geometry.Rectangle2D;

public class Turtle implements Movable{
    private double x;
    private double y;
    private double speed;
    private double width = 60;
    private double height = 30;
    private int level;

    public Turtle(double startX, double startY, double speed, int level) {
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.level = level;
    }

    //Set the length of each movement
    @Override
    public void move() {
        x += speed;
        if (x > FroggerGame.SCREEN_WIDTH + 100) {
            x = -100;
        } else if (x < -100) {
            x = FroggerGame.SCREEN_WIDTH + 100;
        }
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(x - width/2, y - height/2, width, height);
    }

    // Getter method
    @Override
    public double getX() {
        return x;
    }
    @Override
    public double getY() {
        return y;
    }@Override
    public double getSpeed() {
        return speed;
    }
}