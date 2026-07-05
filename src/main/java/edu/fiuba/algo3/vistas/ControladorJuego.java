package edu.fiuba.algo3.vistas;

import javafx.scene.Scene;
import javafx.stage.Stage;
import partida.Partida;
import partida.ResultadoPartida;
import jugadores.Jugador;
import mazo.Mazo;
import mazo.ConfiguracionMazo;
import fases.Fase;
import fases.FaseNocturna;
import roles.Rol;
import votacion.Votacion;
import votacion.ResultadoVotacion;
import nocturno.ResultadoInvestigacion;
import roles.ciudadanos.Sheriff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.scene.control.ButtonType;

public class ControladorJuego {

    private final Stage stage;
    private Partida partida;
    private Map<Jugador, String> mapaNombres;

    public ControladorJuego(Stage stage) {
        this.stage = stage;
        this.mapaNombres = new HashMap<>();
    }

    public void iniciarFlujo() {
        mostrarBienvenida();
    }

    public void mostrarBienvenida() {
        ContenedorBienvenida bienvenida = new ContenedorBienvenida(this);
        stage.setScene(new Scene(bienvenida, 800, 600));
    }

    public void mostrarSetup() {
        ContenedorSetup setup = new ContenedorSetup(this);
        stage.setScene(new Scene(setup, 800, 600));
    }

    public void iniciarPartida(int cantidad, List<String> nombres) {

        ConfiguracionMazo config =
                ConfiguracionMazo.para(cantidad);

        Mazo mazo = new Mazo(config);

        List<Rol> roles =
                mazo.repartir(cantidad);

        List<Jugador> jugadores =
                new ArrayList<>();

        this.mapaNombres.clear();

        for (int i = 0; i < nombres.size(); i++) {

            Jugador jugador =
                    new Jugador(
                            nombres.get(i),
                            roles.get(i)
                    );

            jugadores.add(jugador);
            mapaNombres.put(
                    jugador,
                    nombres.get(i)
            );
        }

        Fase faseInicial =
                new FaseNocturna();

        this.partida =
                new Partida(
                        jugadores,
                        faseInicial
                );

        mostrarFaseNocturna();
    }


    public void mostrarFaseNocturna() {
        ContenedorFaseNocturna contenedorNoche = new ContenedorFaseNocturna(this, this.partida, this.mapaNombres);
        stage.setScene(new Scene(contenedorNoche, 800, 600));
    }

    public void mostrarFaseDiurna() {

        // Si algún Sheriff decidió revelarse durante la noche,
        // se anuncia antes de comenzar la votación.
        for (Jugador jugador : partida.jugadoresVivos()) {

            Optional<ResultadoInvestigacion> resultado =
                    jugador.revelarInvestigacion();

            if (resultado.isPresent()) {

                ResultadoInvestigacion investigacion = resultado.get();

                String bando =
                        investigacion.pareceInocente()
                                ? "🛡️ CIUDADANO"
                                : "🩸 MAFIA";

                AlertaUsuario.mostrarInformacion(
                        "⭐ EL SHERIFF SE REVELA",
                        "El jugador "
                                + mapaNombres.get(jugador).toUpperCase()
                                + " revela públicamente que es el SHERIFF.\n\n"
                                + "Su investigación demuestra que "
                                + mapaNombres.get(investigacion.investigado()).toUpperCase()
                                + " pertenece al bando "
                                + bando
                );

                break;
            }
        }

        ContenedorFaseDiurna contenedorDia =
                new ContenedorFaseDiurna(this, this.partida, this.mapaNombres);

        stage.setScene(new Scene(contenedorDia, 800, 600));
    }

    public void procesarVotacionDiurna(Map<Jugador, Jugador> eleccionesUI, ContenedorFaseDiurna vista) {
        ResultadoVotacion resultado =
                partida.ejecutarVotacion(
                        eleccionesUI
                );

        Optional<Jugador> expulsado =
                resultado.obtenerExpulsado();

        if (expulsado.isPresent()) {

            Jugador jugadorALinchar =
                    expulsado.get();

            AlertaUsuario.mostrarInformacion(
                    "⚖️ Veredicto del Pueblo",
                    "¡El escrutinio ha terminado!\n\nEl jugador "
                            + mapaNombres.get(jugadorALinchar)
                            .toUpperCase()
                            + " ha juntado la mayoría de los votos y fue LINCHADO por el pueblo."
            );

        } else {

            AlertaUsuario.mostrarInformacion(
                    "⚖️ Veredicto del Pueblo",
                    "¡Empate o abstención general!\n\nEl pueblo no logró ponerse de acuerdo y nadie fue linchado esta tarde."
            );
        }

        partida.avanzar();

        ResultadoPartida resultadoVictoria = partida.verificarVictoria();
        if (resultadoVictoria.esTerminal()) {
            vista.mostrarPantallaFinJuego(resultadoVictoria.anunciar());
        } else {
            mostrarFaseNocturna();
        }
    }


    public void ejecutarAccionNocturna(
            Jugador jugador,
            Jugador objetivo) {

        jugador.elegirObjetivo(objetivo);

        String nombreRol = jugador.getRol().nombre();

        if (jugador.esMafia()) {

            AlertaUsuario.mostrarInformacion(
                    "🩸 Voto Registrado",
                    "Tu voto en la reunión de la mafia quedó anotado sobre: "
                            + mapaNombres.get(objetivo)
                            + "\n\nSi hay empate al cierre de la noche, el voto del Padrino decide."
            );

        } else if (nombreRol.equalsIgnoreCase("Detective")|| nombreRol.equalsIgnoreCase("Sheriff")) {

            boolean esSospechoso = objetivo.esSospechoso();
            String bandoRevelado = esSospechoso ? "🩸 BANDO MAFIA" : "🛡️ BANDO CIUDADANO";

            AlertaUsuario.mostrarInformacion(
                    "🕵️‍♂️ Reporte Secreto",
                    "Análisis táctico completado sobre: "
                            + mapaNombres.get(objetivo)
                            + "\n\nFiliación detectada:\n"
                            + bandoRevelado
            );

        } else {

            AlertaUsuario.mostrarInformacion(
                    "Objetivo Confirmado",
                    "✔️ Objetivo fijado con éxito en "
                            + mapaNombres.get(objetivo)
            );
        }
    }


    public void ejecutarAbstencionNocturna(
            Jugador jugador) {

        jugador.abstenerse();
    }

    public void preguntarSiQuiereRevelar(Jugador sheriff) {

        Optional<ButtonType> respuesta =
                AlertaUsuario.mostrarConfirmacion(
                        "Sheriff",
                        "¿Querés revelar públicamente esta investigación durante el próximo día?"
                );

        if (respuesta.isPresent()
                && respuesta.get() == ButtonType.OK) {

            ((Sheriff) sheriff.getRol()).decidirRevelarse();
        }
    }

    public void terminarFaseNocturna(ContenedorFaseNocturna vista) {
        partida.avanzar();
        ResultadoPartida resultado = partida.verificarVictoria();
        if (resultado.esTerminal()) {
            vista.mostrarPantallaFinJuego(resultado.anunciar());
        } else {
            mostrarFaseDiurna();
        }
    }


    public void cerrarJuego() {
        stage.close();
    }
}