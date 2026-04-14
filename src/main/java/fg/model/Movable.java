package fg.model;

public interface Movable {
    public void move();
    double getX();
    double getY();
    double getSpeed();
    javafx.geometry.Rectangle2D getBounds();
}
