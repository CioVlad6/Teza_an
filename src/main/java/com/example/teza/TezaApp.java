package com.example.teza;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TezaApp extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(TezaApp.class.getResource("UIView.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    stage.setTitle("Teza_An");
    stage.setScene(scene);
    stage.show();
  }
  
  public static void main(String[] args) {
    launch();
  }
}