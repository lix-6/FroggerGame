package fg.display;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ucd.comp2011j.engine.Screen;

public class AboutScreen implements Screen {
    private Canvas canvas;

    public AboutScreen() {
        this.canvas = new Canvas(600, 800);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    //Show controls
    public void paint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 24));
        gc.fillText("About Frogger Game", 100, 250);
        gc.fillText("Use the directional keys to control the frog", 100, 300);
        gc.fillText("Press P to pause the game" , 100, 350);
        gc.fillText("Press M to return to the menu" , 100, 400);
    }
}