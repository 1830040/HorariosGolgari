package org.conection_db;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Login1 {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField TFUsuario;

    @FXML
    private Button BUsuario;

    @FXML
    void initialize() {
        assert TFUsuario != null : "fx:id=\"TFUsuario\" was not injected: check your FXML file 'VentanaUsuario.fxml'.";
        assert BUsuario != null : "fx:id=\"BUsuario\" was not injected: check your FXML file 'VentanaUsuario.fxml'.";

    }
}