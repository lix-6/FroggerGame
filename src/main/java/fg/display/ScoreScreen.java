package fg.display;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ucd.comp2011j.engine.Score;
import ucd.comp2011j.engine.ScoreKeeper;

public class ScoreScreen implements ucd.comp2011j.engine.Screen{
    private Canvas canvas;
    private ScoreKeeper scoreKeeper;

    public ScoreScreen(ScoreKeeper scoreKeeper) {
        this.canvas = new Canvas(600, 800);
        this.scoreKeeper = scoreKeeper;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void paint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 36));
        gc.fillText("High Score List", 250, 100);
        gc.setFont(new Font("Arial", 24));

        //Show the scores
        Score[] scores = scoreKeeper.getScores();
        for (int i = 0; i < Math.min(scores.length, 10); i++) {
            gc.fillText((i + 1) + ". " + scores[i], 100, 150 + i * 30);
        }
        gc.fillText("Press M to return to the menu", 200, 500);
    }
}