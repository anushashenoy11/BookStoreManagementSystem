//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.bs;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    public Main() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader splashLoader = new FXMLLoader(this.getClass().getResource("splashscreen.fxml"));
        Parent splashRoot = (Parent)splashLoader.load();
        Scene splashScene = new Scene(splashRoot, 520.0, 464.0);
        FXMLLoader loginLoader = new FXMLLoader(this.getClass().getResource("login.fxml"));
        Parent loginRoot = (Parent)loginLoader.load();
        primaryStage.setTitle("BookStore");
        primaryStage.setScene(splashScene);
        primaryStage.setResizable(false);
        primaryStage.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(2.35));
        pause.setOnFinished((e) -> {
            primaryStage.setScene(new Scene(loginRoot, 520.0, 464.0));
        });
        pause.play();
    }
}
