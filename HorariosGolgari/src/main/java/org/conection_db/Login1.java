package org.conection_db;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login1 {

    //---------------Ventana Usuario---------------------
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TextField TFUsuario;
    @FXML private Button BUsuario;
    
    @FXML void ABUsuario(ActionEvent event) {

    }

    //---------------VContraseniaUs----------------------
    @FXML private Button BContrasenia;
    @FXML private Button BRecuperarContra;
    @FXML private PasswordField PFContrasenia;
    @FXML private Label LUsuario;

    @FXML
    void ABContrasenia(ActionEvent event) {

    }

    @FXML
    void ABRecupContra(ActionEvent event) {

    }

    @FXML
    void initialize() {

        //---------------Ventana Usuario---------------------
        assert TFUsuario != null : "fx:id=\"TFUsuario\" was not injected: check your FXML file 'VentanaUsuario.fxml'.";
        assert BUsuario != null : "fx:id=\"BUsuario\" was not injected: check your FXML file 'VentanaUsuario.fxml'.";

        //---------------VContraseniaUs----------------------
        assert BContrasenia != null : "fx:id=\"BContrasenia\" was not injected: check your FXML file 'VContraseniaUs.fxml'.";
        assert BRecuperarContra != null : "fx:id=\"BRecuperarContra\" was not injected: check your FXML file 'VContraseniaUs.fxml'.";
        assert PFContrasenia != null : "fx:id=\"PFContrasenia\" was not injected: check your FXML file 'VContraseniaUs.fxml'.";
        assert LUsuario != null : "fx:id=\"LUsuario\" was not injected: check your FXML file 'VContraseniaUs.fxml'.";

    }
}