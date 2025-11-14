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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.util.Duration;
import javafx.scene.control.Label;  //import label


import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;
    private static final int HIDDEN_ROWS = 2;

    public Button pauseButton;
    public StackPane pauseMenu;

    @FXML
    private ImageView minTens, minOnes, secTens, secOnes;  //image for timer
    @FXML
    private ImageView lineHundreds, lineTens, lineOnes;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Pane brickOverlay;

    @FXML
    private Pane groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    private Rectangle[][] displayMatrix;
    private Rectangle[][] activeBrick;
    private Rectangle[][] ghostBrick;

    private Timeline timeLine;
    private Timeline timerTimeline;

    private InputEventListener eventListener;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @FXML private ImageView scoreHundreds;
    @FXML private ImageView scoreTens;
    @FXML private ImageView scoreOnes;

    @FXML
    private GridPane nextBlockPane;



    private int timeRemaining = 180;
    private Image[] digits = new Image[10];


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!isPause.get() && !isGameOver.get()) {
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

        loadDigitImages();
        updateScoreImages(0);
        updateLineImages(0);

    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 0; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i);
            }
        }

        activeBrick = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        ghostBrick = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];

        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                // ghost brick
                Rectangle ghost = new Rectangle(BRICK_SIZE - 1, BRICK_SIZE - 1);
                ghost.setFill(Color.LIGHTGRAY);
                ghost.setOpacity(0.3);
                ghost.setArcWidth(9);
                ghost.setArcHeight(9);
                ghostBrick[i][j] = ghost;
                brickOverlay.getChildren().add(ghost);

                // normal brick
                Rectangle active = new Rectangle(BRICK_SIZE - 1, BRICK_SIZE - 1);
                active.setFill(getFillColor(brick.getBrickData()[i][j]));
                active.setStrokeWidth(1.2);
                active.setStrokeType(StrokeType.CENTERED);
                active.setArcWidth(9);
                active.setArcHeight(9);
                activeBrick[i][j] = active;
                brickOverlay.getChildren().add(active);
            }
        }




        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
        startTimer();
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
        // Normal brick
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle r = activeBrick[i][j];
                int value = brick.getBrickData()[i][j];
                r.setVisible(value != 0);
                r.setFill(getFillColor(value));
                r.setX((brick.getxPosition() + j) * BRICK_SIZE);
                r.setY((brick.getyPosition() + i - HIDDEN_ROWS) * BRICK_SIZE);
            }
        }

        // Ghost brick
        int ghostY = calculateGhostY(brick) - 1;

        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle g = ghostBrick[i][j];
                int value = brick.getBrickData()[i][j];

                if (value == 0) {
                    g.setVisible(false);
                    continue;
                }

                g.setVisible(true);
                g.setFill(getFillColor(value));
                g.setOpacity(0.25);
                g.setX((brick.getxPosition() + j) * BRICK_SIZE);
                g.setY((ghostY + i ) * BRICK_SIZE);  // same offset rule
            }
        }
    }

    private int calculateGhostY(ViewData brick) {
        int ghostY = brick.getyPosition();
        // keep moving down until collision
        while (eventListener.canMoveDown(brick, ghostY + 1)) {
            ghostY++;
        }
        return ghostY;
    }

    private void refreshBrick(ViewData brick) {
        if (!isPause.get()) {
            updateBrickPosition(brick);
        }
    }

    public void refreshGameBackground(int[][] board) {
        for (int i = 0; i < board.length; i++) {
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
        if (!isPause.get()) {
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

    public void bindScore(IntegerProperty scoreProperty) {
        scoreProperty.addListener((obs, oldVal, newVal) -> {
            updateScoreImages(newVal.intValue());
        });
    }

    public void lineScore(IntegerProperty lineProperty) {
        lineProperty.addListener((obs, oldVal, newVal) -> {
            updateLineImages(newVal.intValue());
        });
    }

    public void gameOver() {
        timeLine.stop();
        gameOverPanel.setVisible(true);
        isGameOver.setValue(true);
        if (timerTimeline != null) timerTimeline.stop();
    }

    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        gameOverPanel.setVisible(false);
        eventListener.createNewGame();
        gamePanel.requestFocus();
        timeLine.play();
        isPause.set(false);
        isGameOver.set(false);
        timeRemaining = 180;
        startTimer();
    }

    public void resumeGame(ActionEvent actionEvent) {
        pauseMenu.setVisible(false);
        isPause.set(false);
        timeLine.play();
        if (timerTimeline != null) timerTimeline.play();
        gamePanel.requestFocus();
    }

    public void pauseGame(ActionEvent actionEvent) {
        if (isPause.get()) {
            timeLine.play();
            if (timerTimeline != null) timerTimeline.play();
            isPause.set(false);
            pauseMenu.setVisible(false);
        } else {
            if (timerTimeline != null) timerTimeline.pause();
            isPause.set(true);
            pauseMenu.setVisible(true);
        }
        gamePanel.requestFocus();
    }

    public void exitGame(ActionEvent actionEvent) {
        System.exit(0);
    }

    private void loadDigitImages() {
        for (int i = 0; i <= 9; i++) {
            digits[i] = new Image(getClass().getResourceAsStream("/digits/" + i + ".png"));
        }
    }

    private void updateTimerImages(int secondsRemaining) {
        int minutes = secondsRemaining / 60;
        int seconds = secondsRemaining % 60;

        minTens.setImage(digits[minutes / 10]);
        minOnes.setImage(digits[minutes % 10]);
        secTens.setImage(digits[seconds / 10]);
        secOnes.setImage(digits[seconds % 10]);
    }

    private void startTimer() {
        loadDigitImages();
        updateTimerImages(timeRemaining);

        timerTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeRemaining--;
            updateTimerImages(timeRemaining);

            if (timeRemaining <= 0) {
                timerTimeline.stop();
                gameOver();
            }
        }));
        timerTimeline.setCycleCount(Timeline.INDEFINITE);
        timerTimeline.play();
    }
    private void updateScoreImages(int score) {
        int hundreds = (score / 100) % 10;
        int tens = (score / 10) % 10;
        int ones = score % 10;

        scoreHundreds.setImage(digits[hundreds]);
        scoreTens.setImage(digits[tens]);
        scoreOnes.setImage(digits[ones]);
    }

    private void updateLineImages(int lines) {
        int hundreds = (lines / 100) % 10;
        int tens = (lines / 10) % 10;
        int ones = lines % 10;

        lineHundreds.setImage(digits[hundreds]);
        lineTens.setImage(digits[tens]);
        lineOnes.setImage(digits[ones]);
    }

    //show next block
    private static final int BLOCK_SIZE = 20;

    public void showNextBlock(NextShapeInfo next) {
        nextBlockPane.getChildren().clear(); // clear previous preview

        int[][] shape = next.getShape();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    Rectangle rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
                    rect.setFill(getFillColor(shape[i][j])); // reuse your color method
                    rect.setArcWidth(6);
                    rect.setArcHeight(6);
                    rect.setStroke(Color.BLACK);
                    rect.setStrokeWidth(1);

                    nextBlockPane.add(rect, j, i);
                }
            }
        }
    }

}