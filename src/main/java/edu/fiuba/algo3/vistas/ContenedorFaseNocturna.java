package edu.fiuba.algo3.vistas;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import partida.Partida;
import jugadores.Jugador;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContenedorFaseNocturna extends VBox {

    private ControladorJuego controlador;
    private Partida partida;
    private List<Jugador> jugadoresVivos;
    private Map<Jugador, String> mapaNombres;
    private int indiceJugadorActual = 0;

    private final String COLOR_FONDO = "#141414";
    private final String COLOR_ACCION = "#990000";
    private final String COLOR_ACCION_HOVER = "#C00000";
    private final String COLOR_DESTACADO = "#D4AF37";

    private final String ESTILO_BOTON_OBJETIVO = "-fx-background-color: #222222; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-padding: 12 20; -fx-border-color: #444444; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;";
    private final String ESTILO_BOTON_OBJETIVO_HOVER = "-fx-background-color: #333333; -fx-text-fill: #D4AF37; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-padding: 12 20; -fx-border-color: #D4AF37; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;";

    public ContenedorFaseNocturna(ControladorJuego controlador, Partida partida, Map<Jugador, String> mapaNombres) {
        this.controlador = controlador;
        this.partida = partida;
        this.mapaNombres = mapaNombres;

        this.jugadoresVivos = partida.jugadoresVivos();

        this.setAlignment(Pos.CENTER);
        this.setSpacing(25);
        this.setPadding(new Insets(30));
        this.setStyle("-fx-background-color: " + COLOR_FONDO + ";");

        mostrarPantallaDePrivacidad();
    }

    private void mostrarPantallaDePrivacidad() {
        this.getChildren().clear();
        Jugador jugadorActual = jugadoresVivos.get(indiceJugadorActual);

        VBox headerEstado = crearHeaderEstado();

        Label lblInstruccion = new Label("Siguiente turno nocturno. Pásale el dispositivo a:");
        lblInstruccion.setTextFill(Color.web("#AAAAAA"));
        lblInstruccion.setFont(Font.font("Arial", 16));

        Label lblNombre = new Label("👤 " + mapaNombres.get(jugadorActual).toUpperCase());
        lblNombre.setTextFill(Color.WHITE);
        lblNombre.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        Button btnConfirmar = new Button("SOY YO, VER MI CARTA ➔");
        configurarBotonInteractivo(btnConfirmar,
                "-fx-background-color: " + COLOR_ACCION + "; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 15 35; -fx-background-radius: 5; -fx-cursor: hand;",
                "-fx-background-color: " + COLOR_ACCION_HOVER + "; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 15 35; -fx-background-radius: 5; -fx-cursor: hand;");

        btnConfirmar.setOnAction(e -> animarCambioPantalla(() -> mostrarPantallaDeAccion(jugadorActual)));

        this.getChildren().addAll(headerEstado, lblInstruccion, lblNombre, btnConfirmar);
    }

    private void mostrarPantallaDeAccion(Jugador jugadorActual) {
        this.getChildren().clear();
        String nombreRol = jugadorActual.getRol().nombre();

        VBox headerEstado = crearHeaderEstado();
        this.getChildren().add(headerEstado);

        VBox panelOpcionesOcultas = new VBox(20);
        panelOpcionesOcultas.setAlignment(Pos.CENTER);
        panelOpcionesOcultas.setOpacity(0);

        StackPane naipeInteractivo = new StackPane();
        naipeInteractivo.setMaxSize(200, 280);
        naipeInteractivo.setStyle("-fx-cursor: hand;");

        VBox cardBack = new VBox(15);
        cardBack.setAlignment(Pos.CENTER);
        cardBack.setStyle("-fx-background-color: linear-gradient(to bottom, #2c0000, #0f0000); -fx-border-color: " + COLOR_DESTACADO + "; -fx-border-width: 3; -fx-border-radius: 12; -fx-background-radius: 12;");

        Label lblDorsoIcono = new Label("🕵️‍♂️");
        lblDorsoIcono.setFont(Font.font("Arial", 56));
        Label lblDorsoTexto = new Label("TOCÁ LA CARTA\nPARA DARLA VUELTA");
        lblDorsoTexto.setTextFill(Color.web("#CCCCCC"));
        lblDorsoTexto.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblDorsoTexto.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        cardBack.getChildren().addAll(lblDorsoIcono, lblDorsoTexto);

        VBox cardFront = new VBox(15);
        cardFront.setAlignment(Pos.CENTER);
        cardFront.setStyle("-fx-background-color: #222222; -fx-border-color: " + COLOR_DESTACADO + "; -fx-border-width: 3; -fx-border-radius: 12; -fx-background-radius: 12;");
        cardFront.setVisible(false);

        String simboloIlustrativo = "👤";
        String colorIdentificadorTexto = "#FFFFFF";

        if (nombreRol.equalsIgnoreCase("Detective")) {
            simboloIlustrativo = "🔍";
            colorIdentificadorTexto = "#5DADE2";
        } else if (nombreRol.contains("Mafia") || nombreRol.equalsIgnoreCase("Mafioso") || nombreRol.equalsIgnoreCase("Padrino")) {
            simboloIlustrativo = "🩸";
            colorIdentificadorTexto = "#E74C3C";
        }

        Label lblIlustracion = new Label(simboloIlustrativo);
        lblIlustracion.setFont(Font.font("Arial", 64));

        VBox seccionTextoCarta = new VBox(5);
        seccionTextoCarta.setAlignment(Pos.CENTER);
        seccionTextoCarta.setStyle("-fx-background-color: #1a1a1a; -fx-padding: 10; -fx-background-radius: 0 0 9 9;");
        seccionTextoCarta.setPrefWidth(200);

        Label lblRolTextoExplicito = new Label(nombreRol.toUpperCase());
        lblRolTextoExplicito.setTextFill(Color.web(colorIdentificadorTexto));
        lblRolTextoExplicito.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Label lblBandoSubtexto = new Label("Bando Oficial");
        lblBandoSubtexto.setTextFill(Color.LIGHTGRAY);
        lblBandoSubtexto.setFont(Font.font("Arial", 11));
        seccionTextoCarta.getChildren().addAll(lblRolTextoExplicito, lblBandoSubtexto);

        cardFront.getChildren().addAll(lblIlustracion, seccionTextoCarta);
        naipeInteractivo.getChildren().addAll(cardFront, cardBack);

        cardBack.setOnMouseClicked(e -> {
            cardBack.setDisable(true);
            RotateTransition rotarMitad1 = new RotateTransition(Duration.millis(250), naipeInteractivo);
            rotarMitad1.setAxis(Rotate.Y_AXIS);
            rotarMitad1.setFromAngle(0);
            rotarMitad1.setToAngle(90);

            rotarMitad1.setOnFinished(evt -> {
                cardBack.setVisible(false);
                cardFront.setVisible(true);

                RotateTransition rotarMitad2 = new RotateTransition(Duration.millis(250), naipeInteractivo);
                rotarMitad2.setAxis(Rotate.Y_AXIS);
                rotarMitad2.setFromAngle(90);
                rotarMitad2.setToAngle(0);

                rotarMitad2.setOnFinished(finalEvt -> {
                    FadeTransition mostrarMenu = new FadeTransition(Duration.millis(300), panelOpcionesOcultas);
                    mostrarMenu.setToValue(1);
                    mostrarMenu.play();
                });
                rotarMitad2.play();
            });
            rotarMitad1.play();
        });

        this.getChildren().add(naipeInteractivo);

        if (nombreRol.equalsIgnoreCase("Ciudadano")) {
            Label lblInfo = new Label("No posees acciones nocturnas.\nEspera a que el pueblo despierte.");
            lblInfo.setTextFill(Color.web("#BBBBBB"));
            lblInfo.setFont(Font.font("Arial", 15));
            lblInfo.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
            panelOpcionesOcultas.getChildren().add(lblInfo);
        } else {
            Label lblAccion = new Label("🎯 Selecciona tu objetivo estratégico de la noche:");
            lblAccion.setTextFill(Color.WHITE);
            lblAccion.setFont(Font.font("Arial", FontWeight.BOLD, 15));
            panelOpcionesOcultas.getChildren().add(lblAccion);

            GridPane grillaObjetivos = new GridPane();
            grillaObjetivos.setAlignment(Pos.CENTER);
            grillaObjetivos.setHgap(12);
            grillaObjetivos.setVgap(12);

            int col = 0, fila = 0;
            for (Jugador objetivo : jugadoresVivos) {
                Button btnObjetivo = new Button(mapaNombres.get(objetivo));
                configurarBotonInteractivo(btnObjetivo, ESTILO_BOTON_OBJETIVO, ESTILO_BOTON_OBJETIVO_HOVER);

                // 🔥 ACCIÓN MVC: Delegamos la ejecución lógica de la acción nocturna
                btnObjetivo.setOnAction(evt -> {
                    controlador.ejecutarAccionNocturna(jugadorActual, objetivo);
                    animarCambioPantalla(this::avanzarTurno);
                });

                grillaObjetivos.add(btnObjetivo, col, fila);
                col++; if (col > 3) { col = 0; fila++; }
            }
            panelOpcionesOcultas.getChildren().add(grillaObjetivos);
        }

        Button btnDormir = new Button("ABSTENERSE / CONVENTILLO E IR A DORMIR 🌙");
        configurarBotonInteractivo(btnDormir,
                "-fx-background-color: #333333; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-padding: 12 25; -fx-background-radius: 5; -fx-cursor: hand;",
                "-fx-background-color: #444444; -fx-text-fill: " + COLOR_DESTACADO + "; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-padding: 12 25; -fx-background-radius: 5; -fx-cursor: hand;");

        // 🔥 ACCIÓN MVC: Delegamos la abstención nocturna
        btnDormir.setOnAction(evt -> {
            controlador.ejecutarAbstencionNocturna(jugadorActual);
            animarCambioPantalla(this::avanzarTurno);
        });

        panelOpcionesOcultas.getChildren().add(btnDormir);
        this.getChildren().add(panelOpcionesOcultas);
    }

    private void avanzarTurno() {
        indiceJugadorActual++;
        if (indiceJugadorActual < jugadoresVivos.size()) {
            mostrarPantallaDePrivacidad();
        } else {
            // 🔥 ACCIÓN MVC: El controlador evalúa el fin de la noche y decide el próximo escenario
            controlador.terminarFaseNocturna(this);
        }
    }

    private VBox crearHeaderEstado() {
        VBox header = new VBox(2);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10, 20, 15, 20));
        header.setStyle("-fx-border-color: #262626; -fx-border-width: 0 0 1 0;");

        Label lblRonda = new Label("RONDA " + partida.getRondaActual());
        lblRonda.setTextFill(Color.web(COLOR_DESTACADO));
        lblRonda.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label lblFase = new Label("🌃 FASE NOCTURNA");
        lblFase.setTextFill(Color.WHITE);
        lblFase.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        header.getChildren().addAll(lblRonda, lblFase);
        return header;
    }

    private void configurarBotonInteractivo(Button boton, String estiloBase, String estiloHover) {
        boton.setStyle(estiloBase);
        boton.setOnMouseEntered(e -> boton.setStyle(estiloHover));
        boton.setOnMouseExited(e -> boton.setStyle(estiloBase));
    }

    private void animarCambioPantalla(Runnable accionCambio) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(200), this);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(evt -> {
            Platform.runLater(() -> {
                accionCambio.run();
                FadeTransition fadeIn = new FadeTransition(Duration.millis(250), this);
                fadeIn.setToValue(1);
                fadeIn.play();
            });
        });
        fadeOut.play();
    }

    public void mostrarPantallaFinJuego(String mensajeVictoria) {
        this.getChildren().clear();
        Label lblFin = new Label("🏆 FIN DE LA PARTIDA 🏆");
        lblFin.setTextFill(Color.web(COLOR_DESTACADO));
        lblFin.setFont(Font.font("Arial", FontWeight.BOLD, 36));

        Label lblAnuncio = new Label(mensajeVictoria);
        lblAnuncio.setTextFill(Color.WHITE);
        lblAnuncio.setFont(Font.font("Arial", 20));
        lblAnuncio.setWrapText(true);
        lblAnuncio.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        Button btnSalir = new Button("CERRAR JUEGO ✖");
        configurarBotonInteractivo(btnSalir, "-fx-background-color: " + COLOR_ACCION + "; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 15 30;", "-fx-background-color: " + COLOR_ACCION_HOVER + "; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-font-size: 16px; -fx-padding: 15 30;");
        btnSalir.setOnAction(e -> controlador.cerrarJuego());

        this.getChildren().addAll(lblFin, lblAnuncio, btnSalir);
    }
}