package com.comp2042;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.RandomBrickGenerator;

import java.awt.*;

/**
 * Represents the Tetris game board with the current state of bricks and game logic.
 * <p>
 * Handles movement of bricks, rotation, row clearing, score tracking, and next brick preview.
 * Provides methods for game updates and collision detection.
 * </p>
 */
public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private final BrickRotator brickRotator;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;
    private Brick nextBrick;

    /**
     * Constructs a SimpleBoard with the specified width and height.
     * Initializes the matrix, brick generator, brick rotator, and score.
     *
     * @param width  the width of the board in blocks
     * @param height the height of the board in blocks
     */
    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        brickRotator = new BrickRotator();
        score = new Score();
    }

    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);

        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(),
                (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);

        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(),
                (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);

        boolean conflict = MatrixOperations.intersect(currentMatrix, brickRotator.getCurrentShape(),
                (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean rotateLeftBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        NextShapeInfo nextShape = brickRotator.getNextShape();

        boolean conflict = MatrixOperations.intersect(currentMatrix, nextShape.getShape(),
                (int) currentOffset.getX(), (int) currentOffset.getY());

        if (conflict) {
            return false;
        } else {
            brickRotator.setCurrentShape(nextShape.getPosition());
            return true;
        }
    }

    /**
     * Creates a new current brick and prepares the next brick.
     * Handles spawn position and checks for spawn collision.
     *
     * @return {@code true} if the newly spawned brick collides (game over);
     *         {@code false} if spawn is safe
     */
    @Override
    public boolean createNewBrick() {
        // If nextBrick exists, make it the current brick
        Brick currentBrick = nextBrick != null ? nextBrick : brickGenerator.getBrick();
        brickRotator.setBrick(currentBrick);

        // Generate the next brick
        nextBrick = brickGenerator.getBrick();

        // Center and place brick at the top
        int[][] shape = brickRotator.getCurrentShape();
        int shapeWidth = shape[0].length;
        int centerX = (height - shapeWidth) / 2;
        currentOffset = new Point(centerX, 0);

        return MatrixOperations.intersect(currentGameMatrix, brickRotator.getCurrentShape(),
                (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        return new ViewData(brickRotator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY(),
                brickGenerator.getNextBrick().getShapeMatrix().get(0));
    }

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix,
                brickRotator.getCurrentShape(),
                (int) currentOffset.getX(),
                (int) currentOffset.getY());
    }

    /**
     * Clears completed rows, updates the score, and returns information about cleared rows.
     *
     * @return a {@link ClearRow} object containing removed row count and updated matrix
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();

        int rowsCleared = clearRow.getLinesRemoved();
        if (rowsCleared > 0) {
            score.addLine(rowsCleared); // update line counter
        }

        return clearRow;
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        createNewBrick();
    }

    /**
     * Returns the next brick's shape information for preview in the UI.
     *
     * @return {@link NextShapeInfo} of the next brick, or {@code null} if not yet generated
     */
    @Override
    public NextShapeInfo getNextShape() {
        if (nextBrick == null) return null;
        return new NextShapeInfo(nextBrick.getShapeMatrix().get(0), 0); // default rotation index
    }
}
