package org.conection_db;

import configuraciones.ArchivoConfig.*;
import javafx.application.Application;
import java.io.IOException;
import java.sql.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    public static void main( String[] args )
    {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage = FXMLLoader.load(getClass().getResource("/FXML/VentanaUsuario.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

}
