module com.example.movieplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.movieplayer to javafx.fxml;
    exports com.example.movieplayer;
}