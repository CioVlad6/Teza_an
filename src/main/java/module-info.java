module com.example.teza {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;
  
  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires validatorfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires eu.hansolo.tilesfx;
  requires java.desktop;
  requires javafx.swing;
  
  opens com.example.teza to javafx.fxml;
  exports com.example.teza;
  exports com.example.utilities;
  opens com.example.utilities to javafx.fxml;
}