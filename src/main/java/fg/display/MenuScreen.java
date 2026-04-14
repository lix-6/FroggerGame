package fg.display;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ucd.comp2011j.engine.Screen;

public class MenuScreen implements Screen {
    private Canvas canvas;

    public MenuScreen() {
        this.canvas = new Canvas(600, 800);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void paint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFont(new Font("Arial", 36));
        gc.setFill(Color.WHITE);
        gc.fillText("Welcome to Frogger Game!!!!", 80, 200);
        gc.setFont(new Font("Arial", 24));
        gc.fillText("To play a game press N", 150, 300);
        gc.fillText("To see the controls press A",150,350);
        gc.fillText("To see the High scores press H",150,400);
        gc.fillText("To exit press X", 150, 450);
    }
}