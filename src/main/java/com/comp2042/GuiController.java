package com.comp2042;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.control.Label;  //import label


import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;
    public Button pauseButton;
    public StackPane pauseMenu;

    @FXML
    private GridPane gamePanel; // background board

    @FXML
    private Pane brickOverlay;  // active falling brick

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    private Rectangle[][] displayMatrix;  // background blocks
    private Rectangle[][] activeBrick;    // current falling piece
    private Timeline timeLine;
    private InputEventListener eventListener;

    private final BooleanProperty isPause = new SimpleBooleanProperty();

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @FXML
    private Label scoreLabel;   //label class

    @FXML
    private Label timerLabel;   //timer class
    private Timeline timerTimeline;
    private int timeRemaining = 180; // 180 seconds

    @FXML
    private Label linesLabel;  //lines class



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }
                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
            }
        });
        gameOverPanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        activeBrick = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];

        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                int blockSize = BRICK_SIZE - 1;
                Rectangle rectangle = new Rectangle(blockSize, blockSize);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));

                rectangle.setStrokeWidth(1.2);
                rectangle.setStrokeType(javafx.scene.shape.StrokeType.CENTERED);
                rectangle.setArcWidth(9);
                rectangle.setArcHeight(9);

                activeBrick[i][j] = rectangle;
                brickOverlay.getChildren().add(rectangle);

            }
        }




        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
        startCountdownTimer();   //countdown start
    }

    private Paint getFillColor(int i) {
        switch (i) {
            case 0: return Color.TRANSPARENT;
            case 1: return Color.AQUA;
            case 2: return Color.BLUEVIOLET;
            case 3: return Color.DARKGREEN;
            case 4: return Color.YELLOW;
            case 5: return Color.RED;
            case 6: return Color.BEIGE;
            case 7: return Color.BURLYWOOD;
            default: return Color.WHITE;
        }
    }

    private void updateBrickPosition(ViewData brick) {
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle r = activeBrick[i][j];

                r.setX((brick.getxPosition() + j ) * BRICK_SIZE);
                r.setY((brick.getyPosition() + i - 2) * BRICK_SIZE);



                r.setFill(getFillColor(brick.getBrickData()[i][j]));
            }
        }
    }



    private void refreshBrick(ViewData brick) {
        if (!isPause.get()) {
            updateBrickPosition(brick);
        }
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty integerProperty) {
        scoreLabel.textProperty().bind(integerProperty.asString("Score: %d")); //Score label
    }

    private void startCountdownTimer() {
        if (timerTimeline != null) {
            timerTimeline.stop(); // stop old timer if running
        }

        // Reset label and start
        updateTimerLabel();

        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining--;
            updateTimerLabel();

            if (timeRemaining <= 0) {
                timerTimeline.stop();
                gameOver(); // Time’s up, game over display
            }
        }));

        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();
    }

    // helper method to format time
    private void updateTimerLabel() {
        int minutes = timeRemaining / 60;
        int seconds = timeRemaining % 60;
        timerLabel.setText(String.format(" %d:%02d", minutes, seconds));
    }


    public void lineScore(IntegerProperty integerProperty) {
        linesLabel.textProperty().bind(integerProperty.asString("Lines: %d")); //Line label
    }

    public void gameOver() {
        timeLine.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(Boolean.TRUE);
        timerTimeline.stop();    //time stop when game over
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);

        // Reset timer
        timeRemaining = 180; // 3 minutes
        startCountdownTimer();
    }


    public void resumeGame(ActionEvent actionEvent) {
        pauseMenu.setVisible(false);
        isPause.set(false);
        timeLine.play();
        if (timerTimeline != null) timerTimeline.play();
        pauseButton.setText("⏸");
        gamePanel.requestFocus();
    }

    public void exitGame(ActionEvent actionEvent) {
        // Close the game window
        System.out.println("Exiting game");
        System.exit(0); // or switch scene to main menu
    }

    public void pauseGame(ActionEvent actionEvent) {
        if (isPause.get()) {
            // Resume
            timeLine.play();
            if (timerTimeline != null) timerTimeline.play();
            pauseButton.setText("⏸");
            isPause.set(false);
            pauseMenu.setVisible(false); // hide pause menu
        } else {
            // Pause
            timeLine.pause();
            if (timerTimeline != null) timerTimeline.pause();
            pauseButton.setText("▶");
            isPause.set(true);
            pauseMenu.setVisible(true);  // show pause menu
        }
        gamePanel.requestFocus();
    }
}