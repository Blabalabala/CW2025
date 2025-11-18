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
        boolean locked = false;

        if (!canMove) {
            board.mergeBrickToBackground();
            viewGuiController.clearGhostPiece();
            clearRow = board.clearRows();
            locked = true;

            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }

            updateNextBlock();
            viewGuiController.refreshGameBackground(board.getBoardMatrix());
        }

        updateGhostPiece();
        return new DownData(clearRow, board.getViewData(), locked);
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        updateGhostPiece();
        return board.getViewData();
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        updateGhostPiece();
        return board.getViewData();
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateLeftBrick();
        updateGhostPiece();
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

    private void updateGhostPiece() {
        int[][] ghostData = getGhostPiecePosition();
        viewGuiController.drawGhostPiece(ghostData);
    }

    private int[][] getGhostPiecePosition() {
        int[][] currentShape = board.getViewData().getBrickData();
        int currentX = board.getViewData().getxPosition();
        int currentY = board.getViewData().getyPosition();

        int ghostY = currentY;

        // Move down until collision
        while (!checkCollision(currentShape, currentX, ghostY + 1)) {
            ghostY++;
        }

        // Return shape + ghost position
        return new int[][] {
                {currentShape[0][0], currentShape[0][1], currentShape[0][2], currentShape[0][3]},
                {currentShape[1][0], currentShape[1][1], currentShape[1][2], currentShape[1][3]},
                {currentShape[2][0], currentShape[2][1], currentShape[2][2], currentShape[2][3]},
                {currentShape[3][0], currentShape[3][1], currentShape[3][2], currentShape[3][3]},
                {currentX, ghostY}
        };
    }

    private boolean checkCollision(int[][] shape, int x,int y) {
        int[][] boardMatrix = board.getBoardMatrix();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int boardX = x + j;
                    int boardY = y + i;

                    if (boardX < 0 || boardX >= boardMatrix[0].length || boardY >= boardMatrix.length || (boardY >= 0 && boardMatrix[boardY][boardX] != 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    @Override
    public DownData onHardDropEvent(MoveEvent event) {
        DownData downData;
        do {
            downData = onDownEvent(new MoveEvent(EventType.DOWN, EventSource.USER));
        } while (!downData.isLocked());
        return downData;
    }





}
