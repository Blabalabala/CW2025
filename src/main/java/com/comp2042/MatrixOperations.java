package com.comp2042;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for matrix operations in the Tetris game.
 *
 * <p>This class provides static methods to handle common matrix tasks
 * such as collision detection, deep copying, merging bricks into the
 * board, and clearing filled rows. It is not intended to be instantiated.
 */
public class MatrixOperations {

    // Private constructor to prevent instantiation
    private MatrixOperations() { }

    /**
     * Checks if a brick intersects with a given matrix at a specific position.
     *
     * @param matrix the current board matrix
     * @param brick the brick shape matrix
     * @param x the x-coordinate on the board where the brick is placed
     * @param y the y-coordinate on the board where the brick is placed
     * @return true if there is an intersection or out-of-bounds; false otherwise
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0 && (checkOutOfBound(matrix, targetX, targetY) || matrix[targetY][targetX] != 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the given coordinates are out of the board boundaries.
     *
     * @param matrix the board matrix
     * @param targetX x-coordinate
     * @param targetY y-coordinate
     * @return true if out of bounds; false otherwise
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {
        return !(targetX >= 0 && targetY < matrix.length && targetX < matrix[targetY].length);
    }

    /**
     * Creates a deep copy of a 2D integer matrix.
     *
     * @param original the original matrix
     * @return a deep copy of the original matrix
     */
    public static int[][] copy(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] row = original[i];
            copy[i] = new int[row.length];
            System.arraycopy(row, 0, copy[i], 0, row.length);
        }
        return copy;
    }

    /**
     * Merges a brick into a copy of the current board matrix at a specified position.
     *
     * @param filledFields the current board matrix
     * @param brick the brick shape matrix
     * @param x x-coordinate on the board
     * @param y y-coordinate on the board
     * @return a new matrix with the brick merged
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + i;
                int targetY = y + j;
                if (brick[j][i] != 0) {
                    copy[targetY][targetX] = brick[j][i];
                }
            }
        }
        return copy;
    }

    /**
     * Checks the board matrix for fully filled rows and removes them.
     * The cleared rows are replaced with empty rows at the top.
     *
     * <p>The score bonus is calculated as 50 * (number of cleared rows)^2.
     *
     * @param matrix the current board matrix
     * @return a {@link ClearRow} object containing cleared row count, new matrix, and score bonus
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }

        for (int i = matrix.length - 1; i >= 0; i--) {
            int[] row = newRows.pollLast();
            if (row != null) {
                tmp[i] = row;
            } else {
                break;
            }
        }

        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }

    /**
     * Creates a deep copy of a list of 2D integer matrices.
     *
     * @param list the original list of matrices
     * @return a new list containing deep copies of the original matrices
     */
    public static List<int[][]> deepCopyList(List<int[][]> list) {
        return list.stream().map(MatrixOperations::copy).collect(Collectors.toList());
    }
}
