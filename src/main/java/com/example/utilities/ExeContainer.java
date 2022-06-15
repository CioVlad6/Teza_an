package com.example.utilities;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ExeContainer extends Pane {
  private VBox box;
  private Label title;
  private ImageView image;
  private Button run;
  private ContextMenu menu;
  
  private  ExeFile exeFile;
  
  private double startX,startY;
  public ExeContainer(ExeFile exeFile){
    this.exeFile = exeFile;
    generateView();
    addActions();
  }
  
  private void generateView(){
    box = generateBox();
    title = generateTitle(exeFile.name());
    image = generateImage(new File(exeFile.url()));
    run = generateRun("Play");
    menu = generateMenu();
    
    box.getChildren().addAll(title, image, run);
    this.getChildren().add(box);
  }
  private void addActions(){
    setDraggable();
    setContextMenu();
    setRunFile();
  }
  
  
  private void setDraggable(){
    this.setOnMousePressed(mouseEvent -> {
      startX = mouseEvent.getSceneX() - this.getLayoutX();
      startY = mouseEvent.getSceneY() - this.getLayoutY();
      mouseEvent.consume();
    });
    this.setOnMouseDragged(mouseEvent -> {
      if (mouseEvent.getButton() == MouseButton.PRIMARY) {
        this.setLayoutX(mouseEvent.getSceneX() - startX);
        this.setLayoutY(mouseEvent.getSceneY() - startY);
      }
      mouseEvent.consume();
    });
  }
  public void setContextMenu(){
    this.setOnContextMenuRequested(contextMenuEvent -> {
      menu.show(this, contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());
      contextMenuEvent.consume();
    });
  }
  private void setRunFile(){
    run.setOnAction(actionEvent -> {
      try {
        Runtime.getRuntime().exec(exeFile.url());
      } catch (IOException e) {
        e.printStackTrace();
      }
      actionEvent.consume();
    });
  }
  
  
  private VBox generateBox(){
    VBox box = new VBox();
    box.setPrefWidth(165d);
    box.setPrefHeight(225d);
    box.setSpacing(5);
    box.setPadding(new Insets(5,5,5,5));
    box.setStyle("-fx-border-color: black");
    box.setAlignment(Pos.TOP_RIGHT);
    return box;
  }
  private Label generateTitle(String name){
    Label label = new Label(name);
    label.setFont(Font.font("Georgia", FontWeight.BOLD,20));
    label.setPrefWidth(150);
    label.setPrefHeight(20);
    label.setAlignment(Pos.CENTER);
    return label;
  }
  private ImageView generateImage(File sourceFile) {
    ImageIcon gameIcon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(sourceFile);
    BufferedImage bufferedImage = new BufferedImage(150, 150, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D graphics = bufferedImage.createGraphics();
    graphics.drawImage(gameIcon.getImage(), 0, 0, 150, 150, null);
    graphics.dispose();
    return new ImageView(SwingFXUtils.toFXImage(bufferedImage, null));
  }
  private  Button generateRun(String text){
    Button button = new Button(text);
    button.setPrefWidth(70d);
    button.setPrefHeight(25d);
    return button;
  }
  private ContextMenu generateMenu(){
    ContextMenu menu = new ContextMenu();
    menu.getItems().add(new MenuItem("Edit"));
    menu.getItems().add(new MenuItem("Remove"));
    return  menu;
  }
}
