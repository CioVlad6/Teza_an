package com.example.teza;

import com.example.utilities.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UIController implements Initializable {
  @FXML
  BorderPane app;
  @FXML
  AnchorPane library;
  @FXML
  Pane addFileButton;
  ArrayList<ExeFile> exeFileList;
  int rowNr,columnNr, elemNr;
  
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    exeFileList = FileManagement.loadExeFilesList();
    elemNr = exeFileList.size();
    initializeLibrary();
    
    app.setOnKeyPressed(key -> {
      if(key.getCode() == KeyCode.A) selectFile();
      key.consume();
    });
  }
  private void initializeLibrary(){
    columnNr = (int)(library.getPrefWidth()/165);
    rowNr = elemNr/columnNr + 1;
    int height = Math.max(rowNr * 235 + 10, 375);
    library.setPrefHeight(height);
    
    addFileButton.setLayoutX(elemNr%columnNr*175+10);
    addFileButton.setLayoutY((rowNr-1)*235+10);
    addFileButton.setOnMouseClicked(mouseEvent -> {
      selectFile();
      mouseEvent.consume();
    });
    
    exeFileList.forEach(this::addElemToView);
    
    library.setOnDragOver(dragEvent -> {
      if (dragEvent.getGestureSource() != library && dragEvent.getDragboard().hasFiles() &&
        dragEvent.getDragboard().getFiles().stream().map(File::getName).allMatch(name -> name.endsWith(".exe")))
      {
        dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
      }
      dragEvent.consume();
    });
    library.setOnDragDropped(dragEvent -> {
      Dragboard db = dragEvent.getDragboard();
      boolean succes = false;
      if (db.hasFiles()){
        List<File> filesToAdd = db.getFiles();
        filesToAdd.forEach(this::addNewExeFile);
        succes = true;
      }
      dragEvent.setDropCompleted(succes);
      dragEvent.consume();
    });
  }
  
  public void selectFile(){
    FileChooser f_chooser = new FileChooser();
    f_chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(".Exe Files", "*.exe"));
    File selectedFile = f_chooser.showOpenDialog(null);
    
    if (selectedFile == null)
      return;
    addNewExeFile(selectedFile);
  }
  private void addNewExeFile(File selectedFile){
    String gameExeName = selectedFile.getName().replace(".exe","");
    String gameExeUrl = selectedFile.getAbsolutePath();
    ExeFile exeFile = new ExeFile(elemNr++,gameExeName, gameExeUrl);
    
    addElemToView(exeFile);
    FileManagement.updateExeFileList(exeFile);
    exeFileList.add(exeFile);
    
    rowNr = elemNr/columnNr + 1;
    addFileButton.setLayoutX(elemNr%columnNr*175+10);
    addFileButton.setLayoutY((rowNr-1)*235+10);
    library.setPrefHeight(rowNr*235+10);
  }
  private void addElemToView(ExeFile exeFile){
    int nr = exeFile.id();
    int column = nr%columnNr;
    int row = nr/columnNr;
    ExeContainer container = new ExeContainer(exeFile);
    container.setLayoutX(column*175+10);
    container.setLayoutY(row*235+10);
    library.getChildren().add(container);
  }
}