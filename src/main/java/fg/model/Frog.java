package fg.model;

import javafx.geometry.Rectangle2D;
import java.util.List;
import javafx.scene.image.ImageView;

public class Frog implements Movable{
    private double x;
    private double y;
    private double width = 40;
    private double height = 40;
    private double startX;
    private double startY;
    private boolean onMovingObject = false;
    private double platformSpeed = 0;
    private ImageView imageView;
    private int farthestLane = 0;
    private double currentSpeed = 0;

    public Frog(double startX, double startY) {
        this.x = startX;
        this.y = startY;
        this.startX = startX;
        this.startY = startY;

    }

    //Update the position of the image
    public void updatePosition() {
        if (imageView != null) {
            imageView.setX(x - width/2);
            imageView.setY(y - height/2);
        }
    }

    //Check boundaries
    public void moveUp() {
        double newY = y - 50;
        //Ensure that it does not move out of the upper boundary of the screen
        if (newY >= 0) {
            y = newY;
        }
        //Players leave the platform when actively moving
        leaveMovingObject();
    }

    public void moveDown() {
        double newY = y + 50;
        //Ensure that it does not move out of the lower boundary of the screen
        if (newY <= FroggerGame.SCREEN_HEIGHT) {
            y = newY;
        }
        leaveMovingObject();
    }

    public void moveLeft() {
        double newX = x - 50;
        //Ensure that it does not move out of the left boundary of the screen
        if (newX >= 0) {
            x = newX;
        }
        leaveMovingObject();
    }

    public void moveRight() {
        double newX = x + 50;
        // Ensure that it does not move out of the right boundary of the screen
        if (newX <= FroggerGame.SCREEN_WIDTH) {
            x = newX;
        }
        leaveMovingObject();
    }

    //Check collision
    public boolean collidesWith(Vehicle vehicle) {
        Rectangle2D frogRect = new Rectangle2D(x - width/2, y - height/2, width, height);
        Rectangle2D vehicleRect = vehicle.getBounds();
        boolean colliding = frogRect.intersects(vehicleRect);
        return colliding;
    }

    public int getCurrentLane() {
        //Calculate lanes based on the y-coordinate
        //Starting from the bottom, 50 pixels per lane
        return (int) ((FroggerGame.SCREEN_HEIGHT - y) / 50);
    }

    public boolean isInRiver() {
        //Check if it is in the river area
        boolean inRiver = y >= 75 && y <= 300;
        return inRiver;
    }

    public boolean isOnPlatform(List<Wood> woods, List<Turtle> turtles) {
        //Check if standing on wood or turtle
        Rectangle2D frogRect = new Rectangle2D(x - width/2, y - height/2, width, height);

        for (Wood wood : woods) {
            if (frogRect.intersects(wood.getBounds())) {
                return true;
            }
        }

        for (Turtle turtle : turtles) {
            if (frogRect.intersects(turtle.getBounds())) {
                return true;
            }
        }
        return false;
    }

    public void setOnMovingObject(double speed){
        this.onMovingObject = true;
        this.platformSpeed = speed;
        this.currentSpeed = speed;
    }

    public void leaveMovingObject() {
        this.onMovingObject = false;
        this.platformSpeed = 0;
        this.currentSpeed = 0;
    }

    public void moveWithPlatform() {
        if (onMovingObject) {
            x += platformSpeed;
        }
    }

    public boolean isOnSpecificPlatform(Wood wood) {
        Rectangle2D frogRect = new Rectangle2D(x - width/2, y - height/2, width, height);
        return frogRect.intersects(wood.getBounds());
    }

    public boolean isOnSpecificPlatform(Turtle turtle) {
        Rectangle2D frogRect = new Rectangle2D(x - width/2, y - height/2, width, height);
        return frogRect.intersects(turtle.getBounds());
    }

    public boolean isOnMovingObject() {
        return onMovingObject;
    }

    public void resetPosition() {
        x = startX;
        y = startY;
        farthestLane = 0;
        leaveMovingObject();
    }

    @Override
    public void move() {
        //Dealing with frogs moving with the platform
        moveWithPlatform();
    }
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
        return currentSpeed;
    }
    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(x - width/2, y - height/2, width, height);
    }

    //Getter method
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public int getFarthestLane() {
        return farthestLane;
    }
    public void setFarthestLane(int farthestLane) {
        this.farthestLane = farthestLane;
    }
}
