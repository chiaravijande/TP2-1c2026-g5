package edu.fiuba.algo3.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ContenedorBienvenida extends VBox {

    private ControladorJuego controlador;

    private final String COLOR_FONDO = "#141414";
    private final String COLOR_ACCION = "#990000";
    private final String COLOR_ACCION_HOVER = "#C00000";
    private final String COLOR_GOLD = "#D4AF37";

    public ContenedorBienvenida(ControladorJuego controlador) {
        this.controlador = controlador;
        this.setAlignment(Pos.TOP_CENTER);
        this.setSpacing(20);
        this.setPadding(new Insets(25));
        this.setStyle("-fx-background-color: " + COLOR_FONDO + ";");

        GestorSonido.reproducirFondo("mafia.wav");

        configurarContenido();
    }

    private void configurarContenido() {
        ImageView logoView = new ImageView();
        try {
            Image img = new Image(getClass().getResourceAsStream("/imagenes/logoMafia.jpeg"));
            logoView.setImage(img);
            logoView.setFitWidth(550);
            logoView.setPreserveRatio(true);
        } catch (Exception e) {
            Label lblFallback = new Label("LA MAFIA");
            lblFallback.setTextFill(Color.web(COLOR_ACCION));
            lblFallback.setFont(Font.font("Arial", FontWeight.BOLD, 55));
            this.getChildren().add(lblFallback);
        }

        VBox cajaInfo = new VBox(15);
        cajaInfo.setPadding(new Insets(20));
        cajaInfo.setStyle("-fx-background-color: #1c1c1c; -fx-background-radius: 10; -fx-border-color: #292929; -fx-border-width: 1.5; -fx-border-radius: 10;");

        Label lblBienvenida = new Label("¡Bienvenidos a La Mafia!\nUn juego de engaño, traición y deducción social.");
        lblBienvenida.setTextFill(Color.WHITE);
        lblBienvenida.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        lblBienvenida.setTextAlignment(TextAlignment.CENTER);
        lblBienvenida.setMaxWidth(Double.MAX_VALUE);
        lblBienvenida.setAlignment(Pos.CENTER);

        Label lblModalidad = new Label("⚙️ MODALIDAD DE JUEGO: 'PASE Y JUEGUE'\n" +
                "Este dispositivo rotará de mano en mano entre todos los integrantes. " +
                "Cuando la pantalla te asigne el turno, asegúrate de que NADIE más pueda ver tu información secreta.");
        lblModalidad.setTextFill(Color.web("#CCCCCC"));
        lblModalidad.setWrapText(true);
        lblModalidad.setFont(Font.font("Arial", 14));

        VBox rolesInfo = new VBox(12);
        rolesInfo.getChildren().addAll(
                crearLabelRol("🩸 LA MAFIA", "Su objetivo es eliminar sigilosamente a todos los ciudadanos del pueblo sin ser atrapados."),
                crearLabelRol("🎩 EL PADRINO", "Líder estratégico de la mafia. Tiene inmunidad de detección: ante el detective, se muestra como un ciudadano inocente."),
                crearLabelRol("🕵️‍♂️ DETECTIVE", "Cada noche investiga la identidad de un sospechoso para averiguar si pertenece al bando criminal."),
                crearLabelRol("🤠 SHERIFF", "Actúa durante el día. Puede revelar públicamente el resultado de una investigación previa para guiar al pueblo con certezas, aunque al hacerlo expone su rol ante la mafia."),
                crearLabelRol("🩺 MÉDICO", "Cada noche elige a un jugador para protegerlo. Si la mafia intenta atacar a ese jugador, el médico le salvará la vida."),
                crearLabelRol("🛡️ CIUDADANO", "No posee habilidades especiales, pero su palabra y su voto en la Fase Diurna son sus armas más letales para limpiar el pueblo.")
        );

        cajaInfo.getChildren().addAll(lblBienvenida, lblModalidad, rolesInfo);

        ScrollPane scroll = new ScrollPane(cajaInfo);
        scroll.setFitToWidth(true);
        scroll.setPrefHeight(280);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-border-color: transparent;");

        Button btnComenzar = new Button("INGRESAR A LA CIUDAD ➔");
        btnComenzar.setStyle("-fx-background-color: " + COLOR_ACCION + "; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 14 35; -fx-background-radius: 5; -fx-cursor: hand;");
        btnComenzar.setOnMouseEntered(e -> btnComenzar.setStyle("-fx-background-color: " + COLOR_ACCION_HOVER + "; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 14 35; -fx-background-radius: 5; -fx-cursor: hand;"));
        btnComenzar.setOnMouseExited(e -> btnComenzar.setStyle("-fx-background-color: " + COLOR_ACCION + "; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 14 35; -fx-background-radius: 5; -fx-cursor: hand;"));

        // Delegamos en el controlador
        btnComenzar.setOnAction(e -> {
            GestorSonido.detenerFondo();
            controlador.mostrarSetup();
        });

        this.getChildren().addAll(logoView, scroll, btnComenzar);
    }

    private VBox crearLabelRol(String titulo, String desc) {
        Label lblT = new Label(titulo);
        lblT.setTextFill(Color.web(COLOR_GOLD));
        lblT.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        Label lblD = new Label(desc);
        lblD.setTextFill(Color.web("#BBBBBB"));
        lblD.setFont(Font.font("Arial", 13));
        lblD.setWrapText(true);

        return new VBox(3, lblT, lblD);
    }
}
