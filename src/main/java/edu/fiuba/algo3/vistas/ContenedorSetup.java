package edu.fiuba.algo3.vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;

public class ContenedorSetup extends VBox {

    private ControladorJuego controlador;
    private VBox panelNombres;
    private List<TextField> inputsNombres;

    private final String ESTILO_BOTON_PRIMARIO = "-fx-background-color: #B22222; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 24; -fx-background-radius: 5; -fx-cursor: hand;";
    private final String ESTILO_FONDO = "-fx-background-color: #121212;";
    private final String ESTILO_INPUT = "-fx-background-color: #2A2A2A; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-padding: 8; -fx-border-color: #555555; -fx-border-radius: 3; -fx-background-radius: 3;";

    public ContenedorSetup(ControladorJuego controlador) {
        this.controlador = controlador;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(25);
        this.setPadding(new Insets(40));
        this.setStyle(ESTILO_FONDO);
        inicializarFaseCantidad();
    }

    private void inicializarFaseCantidad() {
        this.getChildren().clear();

        Label lblTitulo = new Label("🩸 MAFIA 🩸");
        lblTitulo.setTextFill(Color.web("#E6C229"));
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 36));

        Label lblSubtitulo = new Label("Configuración de Partida");
        lblSubtitulo.setTextFill(Color.WHITE);
        lblSubtitulo.setFont(Font.font("Arial", 18));

        HBox cajaInput = new HBox(15);
        cajaInput.setAlignment(Pos.CENTER);

        Label lblJugadores = new Label("Cantidad de Jugadores (5-12):");
        lblJugadores.setTextFill(Color.WHITE);
        lblJugadores.setFont(Font.font("Arial", 16));

        TextField inputCantidad = new TextField("8");
        inputCantidad.setPrefWidth(60);
        inputCantidad.setStyle(ESTILO_INPUT);
        inputCantidad.setAlignment(Pos.CENTER);

        cajaInput.getChildren().addAll(lblJugadores, inputCantidad);

        Button btnSiguiente = new Button("INGRESAR NOMBRES ➔");
        btnSiguiente.setStyle(ESTILO_BOTON_PRIMARIO);

        btnSiguiente.setOnAction(e -> {
            try {
                int cantidad = Integer.parseInt(inputCantidad.getText());
                if (cantidad < 5 || cantidad > 12) throw new RuntimeException();
                inicializarFaseNombres(cantidad);
            } catch (Exception ex) {
                AlertaUsuario.mostrarError("Error", "⚠️ Ingresá un número válido entre 5 y 12.");
            }
        });

        this.getChildren().addAll(lblTitulo, lblSubtitulo, cajaInput, btnSiguiente);
    }

    private void inicializarFaseNombres(int cantidad) {
        this.getChildren().clear();
        inputsNombres = new ArrayList<>();

        Label lblTitulo = new Label("NOMBRES DE LOS JUGADORES");
        lblTitulo.setTextFill(Color.web("#E6C229"));
        lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        panelNombres = new VBox(15);
        panelNombres.setAlignment(Pos.CENTER);
        panelNombres.setPadding(new Insets(10));

        for (int i = 1; i <= cantidad; i++) {
            HBox filaJugador = new HBox(15);
            filaJugador.setAlignment(Pos.CENTER);
            Label lbl = new Label("👤 Jugador " + i + ":");
            lbl.setTextFill(Color.WHITE);
            lbl.setFont(Font.font("Arial", 14));

            TextField txtNombre = new TextField("Jugador " + i);
            txtNombre.setStyle(ESTILO_INPUT);
            txtNombre.setPrefWidth(200);

            inputsNombres.add(txtNombre);
            filaJugador.getChildren().addAll(lbl, txtNombre);
            panelNombres.getChildren().add(filaJugador);
        }

        ScrollPane scroll = new ScrollPane(panelNombres);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #121212; -fx-background-color: transparent; -fx-control-inner-background: #121212; -fx-border-color: transparent;");
        scroll.setPrefHeight(350);

        Button btnIniciar = new Button("🃏 REPARTIR ROLES E INICIAR");
        btnIniciar.setStyle(ESTILO_BOTON_PRIMARIO);

        //Recolecta strings puros y delega la inicialización de la partida al Controlador
        btnIniciar.setOnAction(e -> {
            try {
                List<String> nombresLista = new ArrayList<>();
                for (TextField txt : inputsNombres) {
                    nombresLista.add(txt.getText().trim());
                }
                controlador.iniciarPartida(cantidad, nombresLista);
            } catch (Exception ex) {
                AlertaUsuario.mostrarError("Error al iniciar", "⚠️ Hubo un problema al configurar la partida.");
            }
        });

        this.getChildren().addAll(lblTitulo, scroll, btnIniciar);
    }
}

