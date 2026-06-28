package edu.fiuba.algo3.vistas;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import partida.Partida;
import jugadores.Jugador;

import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class ContenedorFaseDiurna extends VBox {

        private ControladorJuego controlador;
        private Partida partida;
        private List<Jugador> jugadoresVivos;
        private Map<Jugador, String> mapaNombres;
        private int indiceVotante = 0;

        private Map<Jugador, Jugador> eleccionesUI = new HashMap<>();

        private final String COLOR_FONDO = "#141414";
        private final String COLOR_ACCION = "#990000";
        private final String COLOR_ACCION_HOVER = "#C00000";
        private final String COLOR_DESTACADO = "#D4AF37";

        private final String ESTILO_BOTON_VOTO = "-fx-background-color: #222222; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-padding: 15 22; -fx-border-color: #990000; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;";
        private final String ESTILO_BOTON_VOTO_HOVER = "-fx-background-color: " + COLOR_ACCION + "; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-padding: 15 22; -fx-border-color: #D4AF37; -fx-border-radius: 5; -fx-background-radius: 5; -fx-cursor: hand;";

        public ContenedorFaseDiurna(ControladorJuego controlador, Partida partida, Map<Jugador, String> mapaNombres) {
                this.controlador = controlador;
                this.partida = partida;
                this.mapaNombres = mapaNombres;

                this.jugadoresVivos = partida.jugadoresVivos();

                this.setAlignment(Pos.CENTER);
                this.setSpacing(25);
                this.setPadding(new Insets(30));
                this.setStyle("-fx-background-color: " + COLOR_FONDO + ";");

                mostrarPantallaPrivacidadVoto();
        }

        private void mostrarPantallaPrivacidadVoto() {
                this.getChildren().clear();
                Jugador votanteActual = jugadoresVivos.get(indiceVotante);

                VBox headerEstado = crearHeaderEstado();

                VBox panelCentralUrna = new VBox(12);
                panelCentralUrna.setAlignment(Pos.CENTER);
                panelCentralUrna.setPadding(new Insets(35));
                panelCentralUrna.setMaxWidth(450);
                panelCentralUrna.setStyle("-fx-background-color: #1c1c1c; -fx-background-radius: 10; -fx-border-color: #292929; -fx-border-width: 2; -fx-border-radius: 10;");

                Label lblInstruccion = new Label("Urna de Votación Privada habilitada para:");
                lblInstruccion.setTextFill(Color.web("#AAAAAA"));
                lblInstruccion.setFont(Font.font("Arial", 15));

                Label lblNombre = new Label("👤 " + mapaNombres.get(votanteActual).toUpperCase());
                lblNombre.setTextFill(Color.WHITE);
                lblNombre.setFont(Font.font("Arial", FontWeight.BOLD, 26));

                panelCentralUrna.getChildren().addAll(lblInstruccion, lblNombre);

                Label lblRestantes = new Label("Escrutinio actual: Faltan " + (jugadoresVivos.size() - indiceVotante) + " votos.");
                lblRestantes.setTextFill(Color.web("#777777"));
                lblRestantes.setFont(Font.font("Arial", 13));

                Button btnEntrar = new Button("🗳️ CONFIGURAR MI VOTO");
                configurarBotonInteractivo(btnEntrar, "-fx-background-color: " + COLOR_ACCION + "; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-background-radius: 5; -fx-cursor: hand;", "-fx-background-color: " + COLOR_ACCION_HOVER + "; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 30; -fx-background-radius: 5; -fx-cursor: hand;");

                btnEntrar.setOnAction(e -> animarCambioPantalla(() -> mostrarPanelVotacion(votanteActual)));

                this.getChildren().addAll(headerEstado, panelCentralUrna, btnEntrar, lblRestantes);
        }

        private void mostrarPanelVotacion(Jugador votanteActual) {
                this.getChildren().clear();

                VBox headerEstado = crearHeaderEstado();
                this.getChildren().add(headerEstado);

                Label lblTitulo = new Label("🗳️ Emitir Voto Secreto");
                lblTitulo.setTextFill(Color.web(COLOR_DESTACADO));
                lblTitulo.setFont(Font.font("Arial", FontWeight.BOLD, 22));

                Label lblAviso = new Label("Hola " + mapaNombres.get(votanteActual) + ", selecciona al sospechoso que deseas eliminar:");
                lblAviso.setTextFill(Color.web("#CCCCCC"));
                lblAviso.setFont(Font.font("Arial", 15));
                this.getChildren().addAll(lblTitulo, lblAviso);

                GridPane grillaVotos = new GridPane();
                grillaVotos.setAlignment(Pos.CENTER);
                grillaVotos.setHgap(15);
                grillaVotos.setVgap(15);

                int columna = 0, fila = 0;
                for (Jugador acusado : jugadoresVivos) {
                        Button btnVotar = new Button("Acusar a " + mapaNombres.get(acusado));
                        configurarBotonInteractivo(btnVotar, ESTILO_BOTON_VOTO, ESTILO_BOTON_VOTO_HOVER);

                        btnVotar.setOnAction(e -> {
                                eleccionesUI.put(votanteActual, acusado);
                                animarCambioPantalla(this::avanzarSiguienteVotante);
                        });

                        grillaVotos.add(btnVotar, columna, fila);
                        columna++; if (columna > 3) { columna = 0; fila++; }
                }
                this.getChildren().add(grillaVotos);

                Button btnAbstenerse = new Button("✋ ABSTENERSE DE ACUSACIÓN");
                configurarBotonInteractivo(btnAbstenerse, "-fx-background-color: #333333; -fx-text-fill: white; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-padding: 12 25; -fx-background-radius: 5; -fx-cursor: hand;", "-fx-background-color: #444444; -fx-text-fill: " + COLOR_DESTACADO + "; -fx-font-family: 'Arial'; -fx-font-size: 14px; -fx-padding: 12 25; -fx-background-radius: 5; -fx-cursor: hand;");
                btnAbstenerse.setOnAction(e -> {
                        eleccionesUI.put(votanteActual, null);
                        animarCambioPantalla(this::avanzarSiguienteVotante);
                });
                this.getChildren().add(btnAbstenerse);
        }

        private void avanzarSiguienteVotante() {
                indiceVotante++;
                if (indiceVotante < jugadoresVivos.size()) {
                        mostrarPantallaPrivacidadVoto();
                } else {
                        //Delegamos toda la lógica del negocio de votación al controlador central
                        controlador.procesarVotacionDiurna(eleccionesUI, this);
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

                Label lblFase = new Label("☀️ FASE DIURNA");
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