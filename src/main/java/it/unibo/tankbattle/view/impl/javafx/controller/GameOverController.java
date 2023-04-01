package it.unibo.tankbattle.view.impl.javafx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.tankbattle.view.api.View;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
/**
 * javadock.
 */
public class GameOverController {

    private View viewController;
    private Scene mainManuScene;
    private Scene gameScene;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Button quitButton;

    @FXML
    private Button restartButton;

    @FXML
    private Label winLabel;
    /**
     * javadoc.
     */
    @FXML
    void initialize() {
        assert quitButton != null : "fx:id=\"quitButton\" was not injected: check your FXML file 'gameOver.fxml'.";
        assert restartButton != null : "fx:id=\"restartButton\" was not injected: check your FXML file 'gameOver.fxml'.";
    }
    /**
     * javadoc.
     * @param event param
     */
    @FXML
    void mainMenu(final ActionEvent event) {
        final Stage stage = MainViewController.converterFromEvent(event);
        viewController.newStart();
        stage.setScene(mainManuScene);
        stage.sizeToScene();
    }
    /**
     * javadoc.
     * @param event param
     */
    @FXML
    void quit(final ActionEvent event) {
        Platform.exit();
    }
    /**
     * javadoc.
     * @param event param
     */
    @FXML
    void restart(final ActionEvent event) {
        final Stage stage = MainViewController.converterFromEvent(event);
        viewController.restart();
        stage.setScene(gameScene);
    }
    /**
     * javadoc.
     * @param viewController param
     */
    @SuppressFBWarnings(
        value = {"EI_EXPOSE_REP2"}, 
        justification = "It is needed the object not its copy"
    )
    public void setViewController(final View viewController) {
        this.viewController = viewController;
    }
    /**
     * javadoc.
     * @param gameScene param
     */
    public void setGameScene(final Scene gameScene) {
        this.gameScene = gameScene;
    }
    /**
     * javadoc.
     * @param mainManuScene param
     */
    public void setMenuScene(final Scene mainManuScene) {
        this.mainManuScene = mainManuScene;
    }

    /**
     * javadoc.
     * @param playerName param
     */
    public void setWinLabel(final String playerName) {
        winLabel.setText(playerName + " wins");
    }
}
