package com.example.teza;

import com.example.utilities.ComponentFactory;
import com.example.utilities.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class UIController implements Initializable {
  @FXML
  BorderPane app;
  @FXML
  AnchorPane library;
  @FXML
  Pane addFileButton;
  @FXML
  VBox elemUtilities;
  
  File gameListFile;
  ArrayList<Game> gameList;
  double startX,startY;
  int rowNr,columnNr, elemNr;
  
  private void updateGameList(Game game){
    gameList.add(game);
    rowNr = elemNr/columnNr + 1;
    
    StringBuilder text = new StringBuilder();
    try {
      Scanner in = new Scanner(gameListFile);
      while(in.hasNextLine()){
        text.append(in.nextLine()).append('\n');
      }
      text.append(game.name()).append('|').append(game.url());
    } catch (IOException e) {
      e.printStackTrace();
    }
    try{
      PrintWriter out = new PrintWriter(gameListFile, StandardCharsets.UTF_8);
      out.print(text);
      out.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  
  }
  public void selectFile(){
    FileChooser f_chooser = new FileChooser();
    f_chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".Exe Files", "*.exe"));
    File gameExeFile = f_chooser.showOpenDialog(null);
    if (gameExeFile == null)
      return;
  
    String gameExeName = gameExeFile.getName().replace(".exe","");
    String gameExeUrl = gameExeFile.getAbsolutePath();
    Game game = new Game(elemNr++,gameExeName, gameExeUrl);
    addElemToView(game);
    updateGameList(game);
    addFileButton.setLayoutX(elemNr%columnNr*175+10);
    addFileButton.setLayoutY((rowNr-1)*235+10);
    library.setPrefHeight(rowNr*235+10);
  }
  private void addElemToView(Game game){
    int nr = game.id();
    int column = nr%columnNr;
    int row = nr/columnNr;
    VBox gameShowContainer = ComponentFactory.generateContainer(column, row);
    gameShowContainer.setOnMouseClicked(mouseEvent -> {
      if (mouseEvent.getButton() == MouseButton.SECONDARY){
        elemUtilities.setLayoutX(mouseEvent.getSceneX()-100);
        elemUtilities.setLayoutY(mouseEvent.getSceneY()-25);
        elemUtilities.setViewOrder(-1);
        elemUtilities.setVisible(true);
      }
    });
    gameShowContainer.setOnMousePressed(mouseEvent -> {
      startX = mouseEvent.getSceneX() - gameShowContainer.getLayoutX();
      startY = mouseEvent.getSceneY() - gameShowContainer.getLayoutY();
    });
    gameShowContainer.setOnMouseDragged(mouseEvent -> {
      if (mouseEvent.getButton() == MouseButton.PRIMARY) {
        gameShowContainer.setLayoutX(mouseEvent.getSceneX() - startX);
        gameShowContainer.setLayoutY(mouseEvent.getSceneY() - startY);
      }
    });
  
    Label gameTitle = ComponentFactory.generateLabel(game.name());
    gameShowContainer.getChildren().add(gameTitle);
    
    ImageView gameImageView = new ImageView();
    Image gameImage = ComponentFactory.generateImage(new File(game.url()));
    gameImageView.setImage(gameImage);
    gameShowContainer.getChildren().add(gameImageView);
    
    Button gameRunButton = ComponentFactory.generateButton();
    gameRunButton.setOnAction(actionEvent -> {
      try {
        Runtime.getRuntime().exec(game.url());
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    gameShowContainer.getChildren().add(gameRunButton);
    
    library.getChildren().add(gameShowContainer);
  }
  
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    loadGameFiles();
    initializeLibrary();
    addFileButton.setOnMouseClicked(mouseEvent -> selectFile());
    app.setOnKeyPressed(key -> {
      if(key.getCode() == KeyCode.A){
        selectFile();
      }
    });
  }
  private void initializeLibrary(){
    columnNr = (int)(library.getPrefWidth()/165);
    rowNr = elemNr/columnNr + 1;
    int height = Math.max(rowNr * 235 + 10, 375);
    library.setPrefHeight(height);
    
    addFileButton.setLayoutX(elemNr%columnNr*175+10);
    addFileButton.setLayoutY((rowNr-1)*235+10);
    gameList.forEach(this::addElemToView);
  
    library.setOnMouseClicked(mouseEvent -> {
      if (mouseEvent.getButton() != MouseButton.SECONDARY){
        elemUtilities.setVisible(false);
      }
    });
  }
  
  private void loadGameFiles(){
    try {
      gameListFile = new File("src/main/java/com/example/utilities/gamesList.txt");
      Scanner in = new Scanner(gameListFile);
      gameList = new ArrayList<>();
      int i = 0;
      while(in.hasNextLine()){
        String[] str = in.nextLine().split("\\|");
        gameList.add(new Game(i++,str[0],str[1]));
      }
      elemNr = gameList.size();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}