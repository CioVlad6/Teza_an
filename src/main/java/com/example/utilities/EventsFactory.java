package com.example.utilities;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;

import java.io.File;
import java.io.IOException;
import java.util.List;

abstract public class EventsFactory {
  private static double startX,startY;
  public static void setDraggable(Node node){
    node.setOnMousePressed(mouseEvent -> {
      startX = mouseEvent.getSceneX() - node.getLayoutX();
      startY = mouseEvent.getSceneY() - node.getLayoutY();
      mouseEvent.consume();
    });
    node.setOnMouseDragged(mouseEvent -> {
      if (mouseEvent.getButton() == MouseButton.PRIMARY) {
        node.setLayoutX(mouseEvent.getSceneX() - startX);
        node.setLayoutY(mouseEvent.getSceneY() - startY);
      }
      mouseEvent.consume();
    });
  }
  
  public static void setContextMenu(Node node, Node menu){
    node.setOnContextMenuRequested(contextMenuEvent -> {
      menu.setLayoutX(contextMenuEvent.getSceneX()-100);
      menu.setLayoutY(contextMenuEvent.getSceneY()-25);
      menu.setViewOrder(-1);
      menu.setVisible(true);
      contextMenuEvent.consume();
    });
  }
  
  public static void setRunFile(Button button, String link){
    button.setOnAction(actionEvent -> {
      try {
        Runtime.getRuntime().exec(link);
      } catch (IOException e) {
        e.printStackTrace();
      }
      actionEvent.consume();
    });
  }
}
