package edu.fiuba.algo3.vistas;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Mafia - Juego de Deducción Social");

        // Inicializamos el Controlador central del MVC
        ControladorJuego controlador = new ControladorJuego(stage);
        controlador.iniciarFlujo();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}