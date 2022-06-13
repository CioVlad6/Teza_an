package com.example.utilities;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

abstract public class ComponentFactory {
  public static VBox generateContainer(){
    VBox box = new VBox();
    box.setPrefWidth(165d);
    box.setPrefHeight(225d);
    box.setSpacing(5);
    box.setPadding(new Insets(5,5,5,5));
    box.setStyle("-fx-border-color: black");
    box.setAlignment(Pos.TOP_RIGHT);
    return box;
  }
  public static Label generateLabel(String name){
    Label label = new Label(name);
    label.setFont(Font.font("Georgia", FontWeight.BOLD,20));
    label.setPrefWidth(150);
    label.setPrefHeight(20);
    label.setAlignment(Pos.CENTER);
    return label;
  }
  public static Image generateImage(File sourceFile){
    ImageIcon gameIcon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(sourceFile);
    BufferedImage bufferedImage = new BufferedImage(150, 150, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D graphics = bufferedImage.createGraphics();
    graphics.drawImage(gameIcon.getImage(), 0, 0, 150,150,null);
    graphics.dispose();
    return SwingFXUtils.toFXImage(bufferedImage, null);
  }
  public static Button generateButton(String text){
    Button button = new Button(text);
    button.setPrefWidth(70d);
    button.setPrefHeight(25d);
    return button;
  }
}
