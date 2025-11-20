package com.comp2042;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * A panel that displays temporary notifications, such as score bonuses,
 * with a fade-out and upward movement animation.
 * <p>
 * Typically used in the Tetris game to show points gained when clearing lines.
 * </p>
 */
public class NotificationPanel extends BorderPane {

    /**
     * Constructs a new NotificationPanel with the specified text.
     *
     * @param text the notification text to display (e.g., "+100")
     */
    public NotificationPanel(String text) {
        setMinHeight(200);
        setMinWidth(220);

        final Label score = new Label(text);
        score.getStyleClass().add("bonusStyle");

        final Effect glow = new Glow(0.6);
        score.setEffect(glow);
        score.setTextFill(Color.WHITE);

        setCenter(score);
    }

    /**
     * Animates the notification panel by fading it out and moving it upwards.
     * Once the animation finishes, the panel is removed from the given list of nodes.
     *
     * @param list the ObservableList of nodes from which this panel will be removed after animation
     */
    public void showScore(ObservableList<Node> list) {
        // Fade animation (opacity)
        FadeTransition ft = new FadeTransition(Duration.millis(2000), this);
        ft.setFromValue(1);
        ft.setToValue(0);

        // Translate animation (vertical movement)
        TranslateTransition tt = new TranslateTransition(Duration.millis(2500), this);
        tt.setToY(this.getLayoutY() - 40);

        // Combine fade and translate animations
        ParallelTransition transition = new ParallelTransition(tt, ft);
        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                list.remove(NotificationPanel.this);
            }
        });

        transition.play();
    }
}
