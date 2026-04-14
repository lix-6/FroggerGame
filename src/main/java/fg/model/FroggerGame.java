package fg.model;

import fg.display.PlayerListener;
import ucd.comp2011j.engine.Game;
import java.util.ArrayList;
import java.util.List;

public class FroggerGame implements Game {
    //Game state variables
    private int playerLives;
    private int playerScore;
    private boolean pause = false;
    private boolean gameOver = false;
    private boolean gameWon = false;

    //Screen size
    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 800;

    //Game Object
    private Frog frog;
    private List<Vehicle> vehicles;
    private List<Wood> woods;
    private List<Turtle> turtles;
    private boolean[] homes;  //Record whether 5 homes are occupied

    private boolean upWasPressed = false;
    private boolean downWasPressed = false;
    private boolean leftWasPressed = false;
    private boolean rightWasPressed = false;
    private boolean pauseWasPressed = false;

    //Game progress
    private int currentLevel = 1;
    private int timer;

    //Timer
    private long lastTimerUpdate = 0;
    private int farthestLane = 0;

    private fg.display.PlayerListener listener;

    public FroggerGame(PlayerListener playerListener) {
        this.listener = playerListener;
        startNewGame();
    }

    @Override
    public void startNewGame() {
        playerLives = 3;
        playerScore = 0;
        currentLevel = 1;
        timer = 30;  //30 seconds countdown
        gameWon = false;
        gameOver = false;

        //Initialize Frog (placed in the bottom center)
        frog = new Frog(SCREEN_WIDTH / 2, SCREEN_HEIGHT - 50);

        //Initialize an empty list
        vehicles = new ArrayList<>();
        woods = new ArrayList<>();
        turtles = new ArrayList<>();

        //Initialize homes (5 homes, all empty)
        homes = new boolean[5];
        for (int i = 0; i < homes.length; i++) {
            homes[i] = false;
        }

        //Create obstacles for the first level
        createLevel(currentLevel);
    }

    @Override
    public void updateGame() {
        if (gameWon || gameOver) {
            return;
        }
        if (!isPaused()) {
            //Process keyboard input
            handleInput();
            //Update timer (decreasing per second)
            updateTimer();
            //Use a unified method to move all movable objects
            for (Movable movable : getAllMovables()) {
                movable.move();
            }
            checkFrogPlatform();
            frog.moveWithPlatform();
            //Check collisions
            checkCollisions();
            //Check the condition of game over
            checkGameOver();
        }
    }

    //Get all movable objects
    private List<Movable> getAllMovables() {
        List<Movable> movables = new ArrayList<>();
        movables.addAll(vehicles);
        movables.addAll(woods);
        movables.addAll(turtles);
        return movables;
    }

    //Add keyboard input processing method
    private void handleInput() {
        //Get the current button status
        boolean upNowPressed = listener.isUpPressed();
        boolean downNowPressed = listener.isDownPressed();
        boolean leftNowPressed = listener.isLeftPressed();
        boolean rightNowPressed = listener.isRightPressed();
        boolean pauseNowPressed = listener.isPausePressed();
        //Up key
        if (upNowPressed && !upWasPressed) {
            getFrog().moveUp();
            getFrog().updatePosition();
        }
        upWasPressed = upNowPressed;
        //Down key
        if (downNowPressed && !downWasPressed) {
            getFrog().moveDown();
            getFrog().updatePosition();
        }
        downWasPressed = downNowPressed;
        //Left key
        if (leftNowPressed && !leftWasPressed) {
            getFrog().moveLeft();
            getFrog().updatePosition();
        }
        leftWasPressed = leftNowPressed;
        //Right key
        if (rightNowPressed && !rightWasPressed) {
            getFrog().moveRight();
            getFrog().updatePosition();
        }
        rightWasPressed = rightNowPressed;
        //Pause key
        if (pauseNowPressed && !pauseWasPressed) {
            togglePause();
        }
        pauseWasPressed = pauseNowPressed;
    }

    //Add methods to pause/resume the game
    public void togglePause() {
        this.pause = !this.pause;
    }

    private void checkFrogPlatform() {
        //Assuming the frog is not on the platform at first
        frog.leaveMovingObject();
        //If the frog is in river area, check if it is on wood or turtle
        if(frog.isInRiver()){
            boolean onPlatform = false;
            //Check wood
            for (Wood wood : woods) {
                if (frog.isOnSpecificPlatform(wood)) {
                    frog.setOnMovingObject(wood.getSpeed());
                    onPlatform = true;
                    break;
                }
            }
            //Check turtle
            if (!onPlatform) {
                for (Turtle turtle : turtles) {
                    if (frog.isOnSpecificPlatform(turtle)) {
                        frog.setOnMovingObject(turtle.getSpeed());
                        onPlatform = true;
                        break;
                    }
                }
            }
        }
    }

    private void updateTimer() {
        long currentTime = System.currentTimeMillis();
        //Update timer once per second
        if (currentTime - lastTimerUpdate >= 1000) {
            timer--;
            lastTimerUpdate = currentTime;
            //Lose one life when time is up
            if (timer <= 0) {
                loseLife();
                timer = 30; //Reset timer
            }
        }
    }

    private void createLevel(int level) {
        vehicles.clear();
        woods.clear();
        turtles.clear();
        //Increase the speed of moving objects based on the level
        double vehicleSpeed = 2 + (level - 1) * 0.5;
        int vehicleCount = 3 + (level - 1);
        int denseVehicleCount = vehicleCount + 3;

        //Second and fourth lanes
        for (int i = 0; i < denseVehicleCount / 2 + 1; i++) {
            vehicles.add(new Vehicle(i * 200, 450, vehicleSpeed, true, level,VehicleType.CAR1));
            vehicles.add(new Vehicle(i * 220, 550, vehicleSpeed, true, level,VehicleType.CAR1));
        }
        //The first, third, and fifth lanes
        for (int i = 0; i < denseVehicleCount / 3; i++) {
            vehicles.add(new Vehicle(i * 220, 400, vehicleSpeed + 2, false, level,VehicleType.CAR2));
            vehicles.add(new Vehicle(i * 200, 500, vehicleSpeed, false, level,VehicleType.CAR3));
            vehicles.add(new Vehicle(i * 240, 600, vehicleSpeed + 2, false, level,VehicleType.CAR2));
        }
        //Create a platform with 4 lanes for river areas
        double woodSpeed = 1.5 + (level - 1) * 0.3;
        double turtleSpeed = -2 - (level - 1) * 0.4;

        //Create 5 lanes in the river area from y=100 to y=300
        //Lane 1 (y=100): wood, from left to right
        woods.add(new Wood(-100, 100, woodSpeed, level,WoodType.SHORT));
        woods.add(new Wood(200, 100, woodSpeed, level,WoodType.SHORT));
        woods.add(new Wood(500, 100, woodSpeed, level,WoodType.SHORT));

        //Lane 2 (y=150): turtle, from right to left
        turtles.add(new Turtle(SCREEN_WIDTH + 100, 150, turtleSpeed, level));
        turtles.add(new Turtle(SCREEN_WIDTH - 150, 150, turtleSpeed, level));
        turtles.add(new Turtle(SCREEN_WIDTH - 400, 150, turtleSpeed, level));

        //Lane 3 (y=200): wood, from left to right
        double fasterWoodSpeed = woodSpeed + 0.5;
        woods.add(new Wood(-150, 200, fasterWoodSpeed, level,WoodType.LONG));
        woods.add(new Wood(150, 200, fasterWoodSpeed, level,WoodType.LONG));
        woods.add(new Wood(450, 200, fasterWoodSpeed, level,WoodType.LONG));

        //Lane 4 (y=250): wood, from left to right
        woods.add(new Wood(-150, 250, woodSpeed, level,WoodType.SHORT));
        woods.add(new Wood(150, 250, woodSpeed, level,WoodType.SHORT));
        woods.add(new Wood(450, 250, woodSpeed, level,WoodType.SHORT));

        // Lane 5 (y=300): turtle, from right to left
        turtles.add(new Turtle(SCREEN_WIDTH + 50, 300, turtleSpeed, level));
        turtles.add(new Turtle(SCREEN_WIDTH - 200, 300, turtleSpeed, level));
        turtles.add(new Turtle(SCREEN_WIDTH - 450, 300, turtleSpeed, level));
    }

    //Add a method to obtain the current lane in the Frog class
    public int getCurrentLane() {
        //Calculate lanes based on the y-coordinate
        return (int) ((SCREEN_HEIGHT - frog.getY()) / 50);
    }

    private void checkCollisions() {
        //Check vehicle collisions
        //Check if you have moved forward to a new lane
        int currentLane = getCurrentLane();
        Frog currentFrog = getFrog();
        if (currentLane > currentFrog.getFarthestLane()) {
            int lanesAdvanced = currentLane - currentFrog.getFarthestLane();
            addScore(lanesAdvanced * 150);
            currentFrog.setFarthestLane(currentLane);
        }
        for (Vehicle vehicle : vehicles) {
            if (frog.collidesWith(vehicle)) {
                loseLife();
                return;
            }
        }

        //Check river areas (requires standing on woods or turtles)
        if (frog.isInRiver() && !frog.isOnMovingObject()) {
            loseLife();
            return;
        }
        if (frog.getX() < -frog.getWidth() || frog.getX() > SCREEN_WIDTH + frog.getWidth()) {
            loseLife();
            return;
        }
        //Check if it has arrived at the target home
        checkFrogHome();
    }

    private void checkFrogHome() {
        //Check if the frog has entered the area where the top home is located
        if (frog.getY() >= 25 && frog.getY() <= 65) {
            int homeIndex = -1;
            for(int i = 0; i < 5; i++) {
                double homeLeft = 50 + i * 110;
                double homeRight = homeLeft + 60;
                double frogLeft = frog.getX() - frog.getWidth()/2;
                double frogRight = frog.getX() + frog.getWidth()/2;
                //Check if the center point of the frog is within the x coordinate of this home
                if (frog.getX() >= homeLeft && frog.getX() <= homeRight) {
                    homeIndex = i;
                    break;
                }
            }

            //Handling based on different situations
            if (homeIndex == -1) {
                //Situation 1: x is not within the range of any home (jumping off center)
                loseLife();
            } else if (!homes[homeIndex]) {
                //Situation 2: Jumping into an unoccupied home
                homes[homeIndex] = true;
                addScore(500);
                timer += 30;

                //Show the occupancy status of the current home
                int occupiedCount = 0;
                for (int i = 0; i < homes.length; i++) {
                    if (homes[i]) occupiedCount++;
                }
                resetDestroyedPlayer();

                //Check if all homes are occupied
                if (isLevelFinished()) {
                    levelComplete();
                }
            } else {
                //Jumping into an unoccupied home
                loseLife();
            }
        }
    }

    private void levelComplete() {
        //Level completion reward
        addScore(10000);
        addScore(timer * 250); // Remaining time reward
        if (currentLevel >= 3) {
            gameWon = true;
        } else {
            moveToNextLevel();
        }
    }
    private void checkGameOver() {
        //Check if the game has ended (time is up or life is 0)
        if (timer <= 0 || playerLives <= 0) {
            gameOver = true;
        }
    }

    private void loseLife() {
        playerLives--;

        if (playerLives > 0) {
            resetDestroyedPlayer();
            timer += 30;
        }
    }

    @Override
    public int getPlayerScore() {
        return playerScore;
    }

    @Override
    public boolean isPaused() {
        return pause;
    }

    @Override
    public void checkForPause() {

    }

    @Override
    public boolean isLevelFinished() {
        //Check if all homes are occupied
        int occupiedCount = 0;
        for (boolean home : homes) {
            if (home) occupiedCount++;
        }
        boolean finished = (occupiedCount == 5);
        return finished;
    }

    @Override
    public boolean isPlayerAlive() {
        return playerLives > 0;
    }

    @Override
    public void resetDestroyedPlayer() {
        frog.resetPosition();
    }

    @Override
    public void moveToNextLevel() {
        if (currentLevel >= 3) {
            //The highest level is level 3
            gameWon = true;
            return;
        }
        currentLevel++;
        timer = 30;  //Reset timer
        //Reset the state of home
        for (int i = 0; i < homes.length; i++) {
            homes[i] = false;
        }
        //Create new level
        createLevel(currentLevel);

        resetDestroyedPlayer();
    }

    @Override
    public boolean isGameOver() {
        return playerLives <= 0;
    }

    public Frog getFrog() {
        return frog;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Wood> getWoods() {
        return woods;
    }

    public List<Turtle> getTurtles() {
        return turtles;
    }

    public int getLives() {
        return playerLives;
    }

    public int getLevel() {
        return currentLevel;
    }

    public int getTimer() {
        return timer;
    }

    public boolean[] getHomes() {
        return homes;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void addScore(int points) {
        playerScore += points;
    }

    public void setPaused(boolean paused) {
        this.pause = paused;
    }

    public void checkForPause(PlayerListener listener) {
    }
}