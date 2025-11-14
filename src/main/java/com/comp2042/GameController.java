package com.comp2042;

public class GameController implements InputEventListener {

    private final Board board = new SimpleBoard(25, 10);
    private final GuiController viewGuiController;

    public GameController(GuiController guiController) {
        this.viewGuiController = guiController;
        viewGuiController.setEventListener(this);

        // Initialize the first brick and game view
        board.createNewBrick();
        viewGuiController.initGameView(board.getBoardMatrix(), board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.lineScore(board.getScore().lineProperty());

        // Show the first next block preview
        updateNextBlock();
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow = null;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow = board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            // Update next block preview
            updateNextBlock();
            viewGuiController.refreshGameBackground(board.getBoardMatrix());
        }
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        return board.getViewData();
    }

    @Override
    public void createNewGame() {
        board.newGame();
        board.createNewBrick();
        viewGuiController.refreshGameBackground(board.getBoardMatrix());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.lineScore(board.getScore().lineProperty());

        // Show next block
        updateNextBlock();
    }

    //prevent brick from colliding with other brick
    @Override
    public boolean canMoveDown(ViewData brick, int newY) {
        final int HIDDEN_ROWS = 2;
        int[][] shape = brick.getBrickData();
        int xPos = brick.getxPosition();
        int[][] boardMatrix = board.getBoardMatrix();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int boardY = newY + i - HIDDEN_ROWS;
                    int boardX = xPos + j;

                    if (boardY >= boardMatrix.length || boardX >= boardMatrix[0].length) return false;
                    if (boardMatrix[boardY][boardX] != 0) return false;
                }
            }
        }
        return true;
    }

    //Updates the Next Block preview in the GUI
    private void updateNextBlock() {
        NextShapeInfo nextShape = board.getNextShape();
        if (nextShape != null) {
            viewGuiController.showNextBlock(nextShape);
        }
    }
}
