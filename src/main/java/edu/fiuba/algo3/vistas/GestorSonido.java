package edu.fiuba.algo3.vistas;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class GestorSonido {

    private static MediaPlayer reproductorFondo;

    private GestorSonido() {}

    public static void reproducirEfecto(String nombreArchivo) {

        URL recurso = GestorSonido.class.getResource("/sonidos/" + nombreArchivo);

        if (recurso == null) {
            System.err.println("No se encontró el sonido: " + nombreArchivo);
            return;
        }

        try {
            Media sonido = new Media(recurso.toExternalForm());
            MediaPlayer reproductor = new MediaPlayer(sonido);

            // libera los recursos apenas termina de sonar o si falla
            reproductor.setOnEndOfMedia(reproductor::dispose);
            reproductor.setOnError(reproductor::dispose);

            reproductor.play();

        } catch (Exception e) {
            System.err.println(" Error al reproducir " + nombreArchivo + ": " + e.getMessage());
        }
    }

    public static void reproducirFondo(String nombreArchivo) {

        detenerFondo();

        URL recurso = GestorSonido.class.getResource("/sonidos/" + nombreArchivo);

        if (recurso == null) {
            System.err.println("No se encontró el sonido: " + nombreArchivo);
            return;
        }

        try {
            Media sonido = new Media(recurso.toExternalForm());
            reproductorFondo = new MediaPlayer(sonido);
            reproductorFondo.setCycleCount(MediaPlayer.INDEFINITE);
            reproductorFondo.play();

        } catch (Exception e) {
            System.err.println("Error al reproducir " + nombreArchivo + ": " + e.getMessage());
        }
    }

    public static void detenerFondo() {
        if (reproductorFondo != null) {
            reproductorFondo.stop();
            reproductorFondo.dispose();
            reproductorFondo = null;
        }
    }
}