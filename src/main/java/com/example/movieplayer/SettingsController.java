package com.example.movieplayer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class SettingsController {
    private Parent root;
    @FXML
    private Button buttonClose;
    private float speed;
    private MediaPlayer mediaPlayer;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer)
    {
        this.mediaPlayer = mediaPlayer;
    }
    @FXML
    void closeSettingsDialog(ActionEvent event) {
        // Pobierz źródło zdarzenia (przycisk "Zamknij")
        Node source = (Node) event.getSource();
        // Pobierz scenę, na której znajduje się przycisk
        Scene scene = source.getScene();
        // Pobierz okno dialogowe, które jest właścicielem sceny
        Stage stage = (Stage) scene.getWindow();
        // Zamknij okno dialogowe
        stage.hide();
    }
    @FXML
    void speed1() {
        if (mediaPlayer != null)
            mediaPlayer.setRate(1);
    }
    @FXML
    void speed05() {
        if (mediaPlayer != null)
            mediaPlayer.setRate(0.5f);
    }
    @FXML
    void speed075() {
        if (mediaPlayer != null)
            mediaPlayer.setRate(0.75f);
    }
    @FXML
    void speed15() {
        if (mediaPlayer != null)
            mediaPlayer.setRate(1.5f);
    }
    @FXML
    void speed2() {
        if (mediaPlayer != null)
            mediaPlayer.setRate(2f);
    }
    @FXML
    void skip5() {
        mainController.setForwardTime(5);
    }
    @FXML
    void skip10() {
        mainController.setForwardTime(10);
    }
    @FXML
    void skip15() {
        mainController.setForwardTime(15);
    }
    @FXML
    void skip30() {
        mainController.setForwardTime(30);
    }
    @FXML
    void skip60() {
        mainController.setForwardTime(60);
    }
}
