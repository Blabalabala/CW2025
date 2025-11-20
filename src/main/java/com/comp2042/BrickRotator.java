package com.comp2042;

import com.comp2042.logic.bricks.Brick;

/**
 * Handles rotation of a brick by tracking its current rotation index
 * and providing the next rotated shape.
 */
public class BrickRotator {

    private Brick brick;
    private int currentShape = 0;

    /**
     * Computes the next rotation of the brick without applying it.
     *
     * @return NextShapeInfo containing the rotated shape and rotation index
     */
    public NextShapeInfo getNextShape() {
        int nextShape = currentShape;
        nextShape = (++nextShape) % brick.getShapeMatrix().size();
        return new NextShapeInfo(brick.getShapeMatrix().get(nextShape), nextShape);
    }

    /**
     * Returns the current rotation of the brick.
     *
     * @return a 2D int array representing the current brick shape
     */
    public int[][] getCurrentShape() {
        return brick.getShapeMatrix().get(currentShape);
    }

    /**
     * Sets the current rotation index.
     *
     * @param currentShape the new rotation index
     */
    public void setCurrentShape(int currentShape) {
        this.currentShape = currentShape;
    }

    /**
     * Sets the brick and resets its rotation to zero.
     *
     * @param brick the Brick object to rotate
     */
    public void setBrick(Brick brick) {
        this.brick = brick;
        currentShape = 0;
    }
}
