package fg.display;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class PlayerListener{
    //Mark the status of the directional keys
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean pausePressed = false;
    private boolean pauseProcessed = false;

    public void setListeners(Scene scene){
        scene.setOnKeyPressed(this::handleKeyPressed);
        scene.setOnKeyReleased(this::handleKeyReleased);
        scene.getRoot().setFocusTraversable(true);
        scene.getRoot().requestFocus();
    }

    //When a key is pressed, update the corresponding state
    private void handleKeyPressed(KeyEvent event){
        KeyCode code = event.getCode();
        if (code == KeyCode.UP){
            upPressed = true;
        } else if (code == KeyCode.DOWN){
            downPressed = true;
        } else if (code == KeyCode.LEFT){
            leftPressed = true;
        } else if (code == KeyCode.RIGHT){
            rightPressed = true;
        } else if (code == KeyCode.P) {
            pausePressed = true;
        }
    }

    private void handleKeyReleased(KeyEvent event){
        KeyCode code = event.getCode();
        if (code == KeyCode.UP){
            upPressed = false;
        } else if (code == KeyCode.DOWN){
            downPressed = false;
        } else if (code == KeyCode.LEFT){
            leftPressed = false;
        } else if (code == KeyCode.RIGHT){
            rightPressed = false;
        } else if (code == KeyCode.P) {
            pausePressed = false;
        }

    }

    public boolean isUpPressed(){
        return upPressed;
    }
    public boolean isDownPressed(){
        return downPressed;
    }
    public boolean isLeftPressed(){
        return leftPressed;
    }
    public boolean isRightPressed(){
        return rightPressed;
    }
    public boolean isPausePressed(){
        return pausePressed;
    }
}