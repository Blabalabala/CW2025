package com.comp2042;

public class HoldShapeInfo {
    private final int[][] shape;
    private final int colorCode;

    public HoldShapeInfo(int[][] shape, int colorCode) {
        this.shape = shape;
        this.colorCode = colorCode;
    }

    public int[][] getShape() {
        return shape;
    }

    public int getColorCode() {
        return colorCode;
    }
}

