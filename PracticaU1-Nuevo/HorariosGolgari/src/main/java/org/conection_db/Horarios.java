package org.conection_bd;

import com.jfoenix.controls.JFXDrawersStack;
import com.jfoenix.controls.JFXHamburger;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class Horarios {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXHamburger myHamburger;

    @FXML
    private JFXDrawersStack drawer;

    @FXML
    void initialize() {
        assert myHamburger != null : "fx:id=\"myHamburger\" was not injected: check your FXML file 'VentanaUsuarios.fxml'.";
        assert drawer != null : "fx:id=\"drawer\" was not injected: check your FXML file 'VentanaUsuarios.fxml'.";

        try
        {
            Vbox vbox = FXMLLoader.load(getClass().getResource("/FXML/JFXDrawer.fxml"));
            drawer.setSidePane(vbox);
        }catch (Exception e){
        }

        HamburuerBackArrowBasicTransition transition = new HamburguerBackArrowBasicTransition(myHamburguer);

        myHamburguer.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
            transition.setRate(transition.getRate()*-1);
            transition.play();

            if(drawer.isOpened())
                drawer.close();
            else
                drawer.open();
        });

    }
}