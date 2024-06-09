package com.example.movieplayer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Optional;

public class MainController {

    @FXML
    private Button buttonFastForward;

    @FXML
    private Button buttonForwardToEnd;


    @FXML
    private Button buttonMedia;

    @FXML
    private Button buttonPlay;

    @FXML
    private Button buttonRewind;

    @FXML
    private Button buttonSettings;


    @FXML
    private Button buttonStop;

    @FXML
    private ImageView imageBot;

    @FXML
    private ImageView imageTop;
    @FXML
    private ImageView imageSound;
    @FXML
    private Text labelName;

    @FXML
    private Text labelTime;

    @FXML
    private MediaView mediaView;

    @FXML
    private Slider sliderMovie;
    @FXML
    private ImageView imageButtonPlay;
    @FXML
    private Slider sliderSound;
        private Media media;
    private MediaPlayer mediaPlayer;
    boolean isPlaying = false;
    private Timeline hideUITimeline;
    private final Duration UI_HIDE_DELAY = Duration.seconds(3);
    private Image pauseImage;
    private Image playImage;
    private float skipTime;

    @FXML
    void initialize() throws MalformedURLException {
        File pauseImageFile = new File("src/main/resources/com/example/movieplayer/Images/Pause.png");
        File playImageFile = new File("src/main/resources/com/example/movieplayer/Images/Play.png");
        pauseImage = new Image(pauseImageFile.toURI().toURL().toString());
        playImage = new Image(playImageFile.toURI().toURL().toString());
        skipTime = 5f;
    }
    public void setForwardTime(float time)
    {
        skipTime = time;
    }
    private void setupMouseAndKeyEvents() {
        Scene scene = mediaView.getScene();

        scene.setOnMouseMoved(event -> {
            showUI();
            restartUITimer();
        });
        scene.setOnMouseClicked(event -> {
            showUI();
            restartUITimer();
        });
        hideUITimeline = new Timeline(new KeyFrame(UI_HIDE_DELAY, event -> hideUI()));
        hideUITimeline.setCycleCount(1);
    }

    private void restartUITimer() {
        hideUITimeline.stop();
        hideUITimeline.playFromStart();
    }

    private void showUI() {
        // Pokaż elementy UI
        buttonFastForward.setVisible(true);
        buttonForwardToEnd.setVisible(true);
        buttonMedia.setVisible(true);
        buttonPlay.setVisible(true);
        buttonRewind.setVisible(true);
        buttonSettings.setVisible(true);
        buttonStop.setVisible(true);
        imageBot.setVisible(true);
        imageTop.setVisible(true);
        labelName.setVisible(true);
        labelTime.setVisible(true);
        sliderMovie.setVisible(true);
        sliderSound.setVisible(true);
        imageSound.setVisible(true);
    }

    private void hideUI() {
        // Ukryj elementy UI
        buttonFastForward.setVisible(false);
        buttonForwardToEnd.setVisible(false);
        buttonMedia.setVisible(false);
        buttonPlay.setVisible(false);
        buttonRewind.setVisible(false);
        buttonSettings.setVisible(false);
        buttonStop.setVisible(false);
        imageBot.setVisible(false);
        imageTop.setVisible(false);
        labelName.setVisible(false);
        labelTime.setVisible(false);
        sliderMovie.setVisible(false);
        sliderSound.setVisible(false);
        imageSound.setVisible(false);
    }
    @FXML
    void Play(ActionEvent event) {
        if (mediaPlayer != null)
        {
            if (!isPlaying)
            {
                mediaPlayer.play();
                isPlaying = true;
                imageButtonPlay.setImage(pauseImage);


            }
            else
            {
                mediaPlayer.pause();
                isPlaying = false;
                imageButtonPlay.setImage(playImage);
            }
        }

    }

    @FXML
    void SelectMedia(ActionEvent event) {
        //Stworzenie obiektu odpowiedzialnego za wyświetlenie wyboru pliku
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Movie");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                String url = selectedFile.toURI().toURL().toExternalForm();

                media = new Media(url);
                mediaPlayer = new MediaPlayer(media);

                mediaView.setMediaPlayer(mediaPlayer);

                mediaPlayer.currentTimeProperty().addListener((((observableValue, oldValue, newValue) -> {
                    sliderMovie.setValue(newValue.toSeconds());
                    labelTime.setText(String.format("%02d:%02d/%02d:%02d",
                            (int) newValue.toMinutes(),
                            (int) newValue.toSeconds() % 60,
                            (int) media.getDuration().toMinutes(),
                            (int) media.getDuration().toSeconds() % 60));
                })));

                mediaPlayer.setOnReady(() -> {
                    Duration totalDuration = media.getDuration();
                    sliderMovie.setMax(totalDuration.toSeconds());
                    labelTime.setText(String.format("00:00/%02d:%02d",
                            (int) media.getDuration().toMinutes(),
                            (int) media.getDuration().toSeconds() % 60));
                });
                labelName.setText(selectedFile.getName());
                Scene scene = mediaView.getScene();

                mediaView.fitWidthProperty().bind(scene.widthProperty());
                mediaView.fitHeightProperty().bind(scene.heightProperty());

                setupMouseAndKeyEvents();

                sliderSound.setValue(100);
                sliderSound.valueProperty().addListener((observable, oldValue, newValue) -> {
                    mediaPlayer.setVolume(newValue.doubleValue() / 100);
                    showUI();
                    restartUITimer();
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void sliderPressed(MouseEvent event) {
        if (mediaPlayer != null)
        {
            mediaPlayer.seek(Duration.seconds(sliderMovie.getValue()));
            showUI();
            restartUITimer();
        }

    }
    @FXML
    void StopMedia(ActionEvent event)
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.pause();
            isPlaying = false;
            sliderMovie.setValue(0);
            imageButtonPlay.setImage(playImage);
        }

    }
    @FXML
    void FastEndMedia(ActionEvent event)
    {
        if (mediaPlayer != null)
        {
            mediaPlayer.seek(media.getDuration());
            sliderMovie.setValue(media.getDuration().toSeconds());
            isPlaying = false;
            imageButtonPlay.setImage(playImage);
        }
    }
    @FXML
    void FastForwardMedia(ActionEvent event) {
        if (mediaPlayer != null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            Duration forwardTime = currentTime.add(Duration.seconds(skipTime));
            Duration totalDuration = media.getDuration();

            if (forwardTime.greaterThan(totalDuration)) {
                mediaPlayer.seek(Duration.ZERO);
                FastEndMedia(null);

            } else {
                mediaPlayer.seek(forwardTime);
            }
        }
    }
    @FXML
    void FastBackMedia(ActionEvent event)
    {
        if (mediaPlayer != null) {
            Duration currentTime = mediaPlayer.getCurrentTime();
            Duration forwardTime = currentTime.add(Duration.seconds(-skipTime));

            if (forwardTime.lessThan(Duration.ZERO)) {
                StopMedia(null);
            } else {
                mediaPlayer.seek(forwardTime);
            }
        }
    }
    @FXML
    void openSettingsDialog(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
            Parent root = loader.load();

            SettingsController controller = loader.getController();

            controller.setMediaPlayer(mediaPlayer);
            controller.setMainController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Settings");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
