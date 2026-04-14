package fg.display;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import ucd.comp2011j.engine.*;
import fg.model.FroggerGame;

public class ApplicationStart extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, FroggerGame.SCREEN_WIDTH, FroggerGame.SCREEN_HEIGHT);

        //Create listener
        PlayerListener playerListener = new PlayerListener();
        fg.display.MenuListener menuListener = new fg.display.MenuListener();

        //Set scenes
        menuListener.setListeners(scene);
        playerListener.setListeners(scene);

        primaryStage.setTitle("Frogger Game");

        //Create game instance
        FroggerGame game = new FroggerGame(playerListener);

        //Create each screen
        GameScreen gameScreen = new GameScreen(game);
        MenuScreen menuScreen = new MenuScreen();
        AboutScreen aboutScreen = new AboutScreen();
        ScoreKeeper scoreKeeper = new ScoreKeeper("scores.txt");
        ScoreScreen scoreScreen = new ScoreScreen(scoreKeeper);

        //Manage games using GameManager
        GameManager gameManager = new GameManager((Game) game, root, (MenuCommands) menuListener, menuScreen, aboutScreen, scoreScreen, gameScreen, scoreKeeper);

        //Ensure that the root node can receive the focus
        root.setFocusTraversable(true);
        root.requestFocus();

        menuScreen.paint();
        primaryStage.setScene(scene);
        primaryStage.show();
        gameManager.run();
    }
}