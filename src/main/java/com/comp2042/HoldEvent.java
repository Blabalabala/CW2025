package com.comp2042;

public class HoldEvent {
    private final ViewData newCurrentPiece;
    private final HoldShapeInfo holdPiece;

    public HoldEvent(ViewData newCurrentPiece, HoldShapeInfo holdPiece) {
        this.newCurrentPiece = newCurrentPiece;
        this.holdPiece = holdPiece;
    }

    public ViewData getNewCurrentPiece() {
        return newCurrentPiece;
    }

    public HoldShapeInfo getHoldPiece() {
        return holdPiece;
    }
}

