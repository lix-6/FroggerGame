package fg.display;

import fg.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ucd.comp2011j.engine.Screen;
import java.util.List;
import java.util.ArrayList;

public class GameScreen implements Screen {
    private Canvas canvas;
    private FroggerGame game;
    private GraphicsContext gc;

    public GameScreen(FroggerGame game) {
        this.game = game;
        this.canvas = new Canvas(FroggerGame.SCREEN_WIDTH, FroggerGame.SCREEN_HEIGHT);
        this.gc = canvas.getGraphicsContext2D();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void paint() {
        //Clear the canvas
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //Draw all game elements
        drawBackground(gc);
        drawGameObjects(gc);
        drawUI(gc);
    }

    private void drawBackground(GraphicsContext gc) {
        //Draw background
        gc.setFill(Color.PURPLE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        //Draw homes
        gc.setFill(Color.YELLOWGREEN);
        for (int i = 0; i < 5; i++) {
            gc.fillRect(50 + i * 110, 30, 60, 40);
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(2);
            gc.strokeRect(50 + i * 110, 30, 60, 40);
        }
        //Draw river(blue)
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(0, 80, canvas.getWidth(), 250);

        //Draw road(black)
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 375, canvas.getWidth(), 250);

    }

    private void drawGameObjects(GraphicsContext gc) {
        //Draw all movable objects
        for (Movable movable : getAllMovables()) {
            drawMovable(gc, movable);
        }
        //Draw frogs
        drawFrog(gc, game.getFrog());
        //Draw homes that are occupied
        drawOccupiedHomes(gc);
    }

    //Draw movable objects based on their types
    private void drawMovable(GraphicsContext gc, Movable movable) {
        if (movable instanceof Vehicle) {
            drawVehicleByType(gc, (Vehicle) movable);
        } else if (movable instanceof Wood) {
            drawWoodByType(gc, (Wood) movable);
        } else if (movable instanceof Turtle) {
            drawTurtle(gc, (Turtle) movable);
        }
    }

    //Get all movable objects
    private List<Movable> getAllMovables() {
        List<Movable> movables = new ArrayList<>();
        //Add vehicles one by one
        for (Vehicle vehicle : game.getVehicles()) {
            movables.add(vehicle);
        }
        //Add woods one by one
        for (Wood wood : game.getWoods()) {
            movables.add(wood);
        }
        //Add turtles one by one
        for (Turtle turtle : game.getTurtles()) {
            movables.add(turtle);
        }
        return movables;
    }

    private void drawFrogShape(GraphicsContext gc, double centerX, double centerY, int grid) {
        //Draw legs, arms and hands
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(centerX - 6*grid, centerY - 2*grid, 12*grid, grid);
        gc.fillRect(centerX - 6*grid, centerY + grid, 12*grid, grid);
        gc.fillRect(centerX - 6*grid, centerY - 6*grid, grid, 5*grid);
        gc.fillRect(centerX + 5*grid, centerY - 6*grid, grid, 5*grid);
        gc.fillRect(centerX - 6*grid, centerY + grid, grid, 5*grid);
        gc.fillRect(centerX + 5*grid, centerY + grid, grid, 5*grid);
        gc.fillRect(centerX - 7*grid, centerY - 5*grid, 3*grid, grid);
        gc.fillRect(centerX + 4*grid, centerY - 5*grid, 3*grid, grid);
        gc.fillRect(centerX - 7*grid, centerY + 4*grid, 3*grid, grid);
        gc.fillRect(centerX + 4*grid, centerY + 4*grid, 3*grid, grid);

        // Draw body
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(centerX - 3*grid, centerY - 4*grid, 6*grid, 8*grid);

        // Draw eyes
        gc.setFill(Color.DARKORANGE);
        gc.fillRect(centerX - 2*grid, centerY - 5*grid, grid, grid);
        gc.fillRect(centerX + grid, centerY - 5*grid, grid, grid);

        //Draw the yellow back
        gc.setFill(Color.YELLOW);
        gc.fillRect(centerX - grid, centerY - 2*grid, 2*grid, 4*grid);
    }

    private void drawFrog(GraphicsContext gc, Frog frog) {
        drawFrogShape(gc, frog.getX(), frog.getY(), 3);
    }

    private void drawVehicleByType(GraphicsContext gc, Vehicle vehicle) {
        double x = vehicle.getX();
        double y = vehicle.getY();
        VehicleType type = vehicle.getType();
        // Retrieve grid size from enum
        int grid = type.getGridSize();
        String color = type.getColor();
        switch (type) {
            case CAR1:
                drawVehicle1(gc, vehicle, x, y, grid, color);
                break;
            case CAR2:
                drawVehicle2(gc, vehicle, x, y, grid, color);
                break;
            case CAR3:
                drawVehicle3(gc, vehicle, x, y, grid, color);
                break;
            default://CAR1
                drawVehicle1(gc,vehicle, x, y, grid, color);
        }
    }

    //Draw vehicle 1
    private void drawVehicle1(GraphicsContext gc, Vehicle vehicle, double x, double y, int  grid, String color) {
        double centerX = vehicle.getX();  //Center coordinate X of frog
        double centerY = vehicle.getY();  //Center coordinate Y of frog
        // Draw body
        gc.setFill(Color.WHITE);
        gc.beginPath();
        gc.moveTo(centerX + 10*grid, centerY);
        gc.lineTo(centerX + 7*grid, centerY + 3*grid);
        gc.lineTo(centerX - 9*grid, centerY + 3*grid);
        gc.lineTo(centerX - 10*grid, centerY + 2*grid);
        gc.lineTo(centerX - 4*grid, centerY + 2*grid);
        gc.lineTo(centerX - 4*grid, centerY - 2*grid);
        gc.lineTo(centerX - 10*grid, centerY - 2*grid);
        gc.lineTo(centerX - 9*grid, centerY - 3*grid);
        gc.lineTo(centerX + 7*grid, centerY - 3*grid);
        gc.closePath();
        gc.fill();

        //Draw the connections between the body and wheels
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(centerX + 3*grid, centerY - 4*grid, grid, grid);
        gc.fillRect(centerX + 3*grid, centerY + 3*grid, grid, grid);
        gc.fillRect(centerX - 7*grid, centerY - 4*grid, grid, grid);
        gc.fillRect(centerX - 7*grid, centerY + 3*grid, grid, grid);

        //Draw wheels
        gc.setFill(Color.RED);
        gc.fillRect(centerX - 8*grid, centerY - 6*grid, 3*grid, 2*grid);
        gc.fillRect(centerX - 8*grid, centerY + 4*grid, 3*grid, 2*grid);
        gc.fillRect(centerX + 2*grid, centerY - 5*grid, 3*grid, grid);
        gc.fillRect(centerX + 2*grid, centerY + 4*grid, 3*grid, grid);

        //Draw decorations
        gc.setFill(Color.RED);
        gc.fillRect(centerX - 8*grid, centerY - grid, grid, 2*grid);
        gc.fillRect(centerX - 5*grid, centerY - grid, grid, 2*grid);
        gc.fillRect(centerX - 2*grid, centerY - grid, grid, 2*grid);
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(centerX - 11*grid, centerY - 2*grid, 7*grid, grid);
        gc.fillRect(centerX - 11*grid, centerY + grid, 7*grid, grid);
        gc.setFill(Color.LIGHTGREEN);
        gc.beginPath();
        gc.moveTo(centerX + 5*grid, centerY);
        gc.lineTo(centerX + 4*grid, centerY + 2*grid);
        gc.lineTo(centerX + grid, centerY + 2*grid);
        gc.lineTo(centerX + grid, centerY + grid);
        gc.lineTo(centerX + 2*grid, centerY + grid);
        gc.lineTo(centerX + 2*grid, centerY - grid);
        gc.lineTo(centerX + grid, centerY - grid);
        gc.lineTo(centerX + grid, centerY - 2*grid);
        gc.lineTo(centerX + 4*grid, centerY - 2*grid);
        gc.closePath();
        gc.fill();
    }

    //Draw vehicle 2
    private void drawVehicle2(GraphicsContext gc, Vehicle vehicle, double x, double y, int grid, String color) {
        double centerX = vehicle.getX();  //Center coordinate X of vehicle
        double centerY = vehicle.getY();  //Center coordinate X of vehicle
        // Draw body
        gc.setFill(Color.YELLOW);
        gc.beginPath();
        gc.moveTo(centerX - 10*grid, centerY);
        gc.lineTo(centerX - 7*grid, centerY + 3*grid);
        gc.lineTo(centerX + 9*grid, centerY + 3*grid);
        gc.lineTo(centerX + 10*grid, centerY + 2*grid);
        gc.lineTo(centerX + 4*grid, centerY + 2*grid);
        gc.lineTo(centerX + 4*grid, centerY - 2*grid);
        gc.lineTo(centerX + 10*grid, centerY - 2*grid);
        gc.lineTo(centerX + 9*grid, centerY - 3*grid);
        gc.lineTo(centerX - 7*grid, centerY - 3*grid);
        gc.closePath();
        gc.fill();

        //Draw the connections between the body and wheels
        gc.setFill(Color.PURPLE);
        gc.fillRect(centerX - 3*grid, centerY - 4*grid, grid, grid);
        gc.fillRect(centerX - 3*grid, centerY + 3*grid, grid, grid);
        gc.fillRect(centerX + 7*grid, centerY - 4*grid, grid, grid);
        gc.fillRect(centerX + 7*grid, centerY + 3*grid, grid, grid);

        //Draw wheels
        gc.setFill(Color.RED);
        gc.fillRect(centerX + 6*grid, centerY - 6*grid, 3*grid, 2*grid);
        gc.fillRect(centerX + 6*grid, centerY + 4*grid, 3*grid, 2*grid);
        gc.fillRect(centerX - 4*grid, centerY - 5*grid, 3*grid, grid);
        gc.fillRect(centerX - 4*grid, centerY + 4*grid, 3*grid, grid);

        //Draw decorations
        gc.setFill(Color.RED);
        gc.fillRect(centerX + grid, centerY - grid, grid, 2*grid);
        gc.fillRect(centerX + 4*grid, centerY - grid, grid, 2*grid);
        gc.fillRect(centerX + 7*grid, centerY - grid, grid, 2*grid);
        gc.setFill(Color.PURPLE);
        gc.fillRect(centerX + 4*grid, centerY - 2*grid, 7*grid, grid);
        gc.fillRect(centerX + 4*grid, centerY + grid, 7*grid, grid);
        gc.setFill(Color.PURPLE);
        gc.beginPath();
        gc.moveTo(centerX - 5*grid, centerY);
        gc.lineTo(centerX - 4*grid, centerY + 2*grid);
        gc.lineTo(centerX - grid, centerY + 2*grid);
        gc.lineTo(centerX - grid, centerY + grid);
        gc.lineTo(centerX - 2*grid, centerY + grid);
        gc.lineTo(centerX - 2*grid, centerY - grid);
        gc.lineTo(centerX - grid, centerY - grid);
        gc.lineTo(centerX - grid, centerY - 2*grid);
        gc.lineTo(centerX - 4*grid, centerY - 2*grid);
        gc.closePath();
        gc.fill();

    }

    //Draw vehicle 3
    private void drawVehicle3(GraphicsContext gc, Vehicle vehicle, double x, double y, int grid, String color) {
        double centerX = vehicle.getX();  //Center X coordinate of vehicle
        double centerY = vehicle.getY();  //Center Y coordinate of vehicle
        // Draw body
        gc.setFill(Color.LIGHTPINK);
        gc.fillOval(centerX - 6*grid, centerY - 4*grid, 6*grid, 8*grid);
        gc.fillRect(centerX - grid, centerY - 2*grid, 2*grid, 4*grid);

        gc.setFill(Color.LIGHTPINK);
        gc.beginPath();
        gc.moveTo(centerX + grid, centerY - 2*grid);
        gc.lineTo(centerX + 2*grid, centerY - 4*grid);
        gc.lineTo(centerX + 4*grid, centerY - 4*grid);
        gc.lineTo(centerX + 5*grid, centerY - 3*grid);
        gc.lineTo(centerX + 6*grid, centerY - grid);
        gc.lineTo(centerX + 6*grid, centerY + grid);
        gc.lineTo(centerX + 5*grid, centerY + 3*grid);
        gc.lineTo(centerX + 4*grid, centerY + 4*grid);
        gc.lineTo(centerX + 2*grid, centerY + 4*grid);
        gc.lineTo(centerX + grid, centerY + 2*grid);
        gc.closePath();
        gc.fill();

        //Draw wheels
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(centerX - 4*grid, centerY - 5*grid, 2*grid, grid);
        gc.fillRect(centerX + 2*grid, centerY - 5*grid, 2*grid, grid);
        gc.fillRect(centerX - 4*grid, centerY + 4*grid, 2*grid, grid);
        gc.fillRect(centerX + 2*grid, centerY + 4*grid, 2*grid, grid);

        //Draw decorations
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(centerX - 5*grid, centerY - 2*grid, 2*grid, grid);
        gc.fillRect(centerX - 5*grid, centerY + grid, 2*grid, grid);
        gc.setFill(Color.BLUE);
        gc.fillRect(centerX - 6*grid, centerY - 2*grid, grid, 4*grid);
        gc.fillRect(centerX + 3*grid, centerY - grid, grid, 2*grid);
        gc.fillRect(centerX + 5*grid, centerY - 2*grid, 2*grid, grid);
        gc.fillRect(centerX + 5*grid, centerY + grid, 2*grid, grid);
        gc.fillOval(centerX - grid, centerY - grid, 2*grid, 2*grid);
    }

    //Draw different length of wood by type
    private void drawWoodByType(GraphicsContext gc, Wood wood) {
        double x = wood.getX();
        double y = wood.getY();
        WoodType type = wood.getType();

        switch (type) {
            case SHORT:
                drawWood1(gc, wood);
                break;
            case LONG:
                drawWood2(gc, wood);
                break;
            default: //Short wood
                drawWood1(gc, wood);
        }
    }

    private void drawWood1(GraphicsContext gc, Wood wood) {
        double centerX = wood.getX();  //Center X coordinate of wood
        double centerY = wood.getY();  //Center X coordinate of wood
        int grid = wood.getType().getGridSize();
        //Draw body
        gc.setFill(Color.BROWN);
        gc.fillOval(centerX - 12*grid, centerY - 3*grid, 4*grid, 6*grid);
        gc.fillRect(centerX - 10*grid, centerY - 3*grid, 20*grid, 6*grid);
        gc.setFill(Color.WHITE);
        gc.fillOval(centerX + 8*grid, centerY - 3*grid, 4*grid, 6*grid);
        gc.setFill(Color.BROWN);
        gc.fillOval(centerX + 9*grid, centerY - 2*grid, 2*grid, 4*grid);

        //Draw decorations
        gc.setFill(Color.WHITE);
        gc.fillRect(centerX - 11*grid, centerY, 2*grid, grid/2);
        gc.fillRect(centerX - 10*grid, centerY - 2*grid, 2*grid, grid/2);
        gc.fillRect(centerX - 5*grid, centerY - grid, 2*grid, grid/2);
        gc.fillRect(centerX + 2*grid, centerY, 2*grid, grid/2);
        gc.fillRect(centerX + 5*grid, centerY + grid, 2*grid, grid/2);
        gc.fillRect(centerX + 4*grid, centerY - 2*grid, grid, grid/2);
    }

    private void drawWood2(GraphicsContext gc, Wood wood) {
        double centerX = wood.getX();  //Center X coordinate of wood
        double centerY = wood.getY();  //Center X coordinate of wood
        int grid = wood.getType().getGridSize();
        //Draw body
        gc.setFill(Color.BROWN);
        gc.fillOval(centerX - 17*grid, centerY - 3*grid, 4*grid, 6*grid);
        gc.fillRect(centerX - 15*grid, centerY - 3*grid, 30*grid, 6*grid);
        gc.setFill(Color.WHITE);
        gc.fillOval(centerX + 13*grid, centerY - 3*grid, 4*grid, 6*grid);
        gc.setFill(Color.BROWN);
        gc.fillOval(centerX + 14*grid, centerY - 2*grid, 2*grid, 4*grid);

        //Draw decorations
        gc.setFill(Color.WHITE);
        gc.fillRect(centerX - 16*grid, centerY, 2*grid, grid/2);
        gc.fillRect(centerX - 15*grid, centerY - 2*grid, 2*grid, grid/2);
        gc.fillRect(centerX - 10*grid, centerY - grid, 2*grid, grid/2);
        gc.fillRect(centerX - 7*grid, centerY + grid, 2*grid, grid/2);
        gc.fillRect(centerX + 2*grid, centerY, 2*grid, grid/2);
        gc.fillRect(centerX + grid, centerY, grid, grid/2);
        gc.fillRect(centerX + 5*grid, centerY + grid, 2*grid, grid/2);
        gc.fillRect(centerX + 10*grid, centerY + grid, 2*grid, grid/2);
        gc.fillRect(centerX + 8*grid, centerY + 2*grid, grid, grid/2);
        gc.fillRect(centerX + 8*grid, centerY - 2*grid, 2*grid, grid/2);
    }

    private void drawTurtle(GraphicsContext gc, Turtle turtle) {
        double centerX = turtle.getX();  // Center coordinate X of turtle
        double centerY = turtle.getY();  // Center coordinate Y of turtle
        double grid = 4.5;
        //Draw legs
        gc.setFill(Color.YELLOWGREEN);
        gc.fillRect(centerX - 9*grid, centerY - 4*grid, 2*grid, grid);
        gc.fillRect(centerX - 8*grid, centerY - 4*grid, grid, 8*grid);
        gc.fillRect(centerX - 9*grid, centerY + 3*grid, 2*grid, grid);
        gc.fillRect(centerX - 3*grid, centerY - 4*grid, 2*grid, grid);
        gc.fillRect(centerX - 3*grid, centerY - 4*grid, grid, 8*grid);
        gc.fillRect(centerX - 3*grid, centerY + 3*grid, 2*grid, grid);
        gc.fillRect(centerX + 2*grid, centerY - 4*grid, 2*grid, grid);
        gc.fillRect(centerX + 3*grid, centerY - 4*grid, grid, 8*grid);
        gc.fillRect(centerX + 2*grid, centerY + 3*grid, 2*grid, grid);
        gc.fillRect(centerX + 8*grid, centerY - 4*grid, 2*grid, grid);
        gc.fillRect(centerX + 8*grid, centerY - 4*grid, grid, 8*grid);
        gc.fillRect(centerX + 8*grid, centerY + 3*grid, 2*grid, grid);

        //Draw body
        gc.setFill(Color.RED);
        gc.fillOval(centerX - 9*grid, centerY - 3*grid, 8*grid, 6*grid);
        gc.fillOval(centerX + 2*grid, centerY - 3*grid, 8*grid, 6*grid);
        gc.setFill(Color.WHITE);
        gc.fillRect(centerX - 6*grid, centerY + 1.5*grid, 2*grid, 0.5*grid);
        gc.fillRect(centerX + 5*grid, centerY + 1.5*grid, 2*grid, 0.5*grid);

        //Draw head and tail
        gc.setFill(Color.YELLOWGREEN);
        gc.fillOval(centerX - 11*grid, centerY - grid, 2*grid, 2*grid);
        gc.fillOval(centerX, centerY - grid, 2*grid, 2*grid);
        gc.fillRect(centerX - grid, centerY, 0.6*grid, grid);
        gc.fillRect(centerX + 10*grid, centerY, 0.6*grid, grid);
    }

    private void drawOccupiedHomes(GraphicsContext gc) {
        boolean[] homes = game.getHomes();
        for (int i = 0; i < homes.length; i++) {
            if (homes[i]) {
                double homeCenterX = 55 + i * 110 + 25;
                double homeCenterY = 35 + 15;
                //Set the appearance of the frog at home(same with outside)
                drawFrogShape(gc, homeCenterX, homeCenterY, 3);
            }
        }
    }

    private void drawUI(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 16));
        //print the information of score, live, level and time
        gc.fillText("Score: " + game.getPlayerScore(), 10, 650);
        gc.fillText("Lives: " + game.getLives(), 10, 665);
        gc.fillText("Level: " + game.getLevel(), 10, 680);
        gc.fillText("Time: " + game.getTimer() + "Seconds", 450, 680);
        //Set pause
        if (game.isPaused()) {
            gc.setFill(Color.WHITE);
            gc.fillText("Pause, press P to continue", 200, 360);
        }
        if (game.isGameWon()) {
            gc.setFill(Color.GREEN);
            gc.setFont(new Font("Arial", 36));
            gc.fillText("VICTORY!", canvas.getWidth() / 2 - 70, 300);

            gc.setFont(new Font("Arial", 20));
            gc.fillText("Congratulations! You beat all levels!", canvas.getWidth() / 2 - 150, 350);
            gc.fillText("Final Score: " + game.getPlayerScore(), canvas.getWidth() / 2 - 80, 400);
        }
        if (game.isGameOver()) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Arial", 24));
            gc.fillText("Game Over!!!", 250, 300);
        }
    }
}