package com.comp2042;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
/**
 * Main entry point for the Tetris JavaFX application.
 *
 * This class extends {@link javafx.application.Application} and initializes
 * the JavaFX stage, loads the FXML layout, sets up the scene, and starts
 * the game controller. It also plays background music on launch.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if loading the FXML or initializing the scene fails
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        MusicPlayerWav musicPlayer = new MusicPlayerWav();
        musicPlayer.playMusic("/chill_music.wav");   // play background music

        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();

        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, 300, 510);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);    // Full screen
        primaryStage.show();

        new GameController(c);
    }
}
