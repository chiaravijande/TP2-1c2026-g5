package edu.fiuba.algo3.vistas;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertaUsuario {

    /**
     * Muestra un popup en pantalla bloqueando el flujo hasta que el usuario le da "OK".
     */
    public static void mostrarError(String cabecera, String mensajeSecundario) {
        Alert alerta = new Alert(AlertType.ERROR);
        alerta.setTitle("Atención");
        alerta.setHeaderText(cabecera);
        alerta.setContentText(mensajeSecundario);
        alerta.showAndWait();
    }

    public static void mostrarInformacion(String titulo, String mensaje) {
        Alert alerta = new Alert(AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
