package com.comp2042;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * GameOverPanel represents the UI panel displayed when the game ends.
 * It extends BorderPane and shows a centered "GAME OVER" label.
 */
public class GameOverPanel extends BorderPane {

    /**
     * Constructs a GameOverPanel with a centered "GAME OVER" label.
     * The label uses the CSS style class "gameOverStyle".
     */
    public GameOverPanel() {
        final Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.getStyleClass().add("gameOverStyle");
        setCenter(gameOverLabel);
    }
}
