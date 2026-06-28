package partida;

import jugadores.Jugador;
import fases.Fase;
import mazo.Mazo;
import votacion.ResultadoVotacion;
import votacion.Votacion;

import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;

public class Partida {

    private int rondaActual;
    private List<Jugador> jugadores;
    private Fase faseActual;

    //el constructor inicializa la partida con los jugadores, el mazo configurado y la primera fase
    public Partida(List<Jugador> jugadores, Fase faseInicial) {
        this.jugadores = jugadores;
        this.faseActual = faseInicial;
        this.rondaActual = 1;
    }

    /*//ejecuta la fase actual y luego le pide que avance a la siguiente
    public void avanzar() {
        this.faseActual.ejecutar(this);
        this.faseActual = this.faseActual.siguienteFase();

        //ver: podriamos incrementar la ronda si la nueva fase es una FaseDiurna, y no siempre que se cambie. (ver logica de incremento de fases).
    }*/
    public void avanzar() {

        this.faseActual.ejecutar(this);

        Fase siguiente =
                this.faseActual.siguienteFase(this);
        if (siguiente.iniciaNuevaRonda()) {
            rondaActual++;
        }

        faseActual = siguiente;
    }

    //utiliza ContadorBandos para determinar si la partida termino
    public ResultadoPartida verificarVictoria() {
        ContadorDeBandos contador = new ContadorDeBandos();

        //cada jugador vivo se suma al contador segun su bando
        for (Jugador jugador : this.jugadores) {
            jugador.agruparseEn(contador);
        }

        return contador.evaluarCondicion();
    }

    //delega al jugador cambiarse a estado muerto
    public void eliminarJugador(Jugador jugador) {
        jugador.eliminar();
    }

    public List<Jugador> getJugadores() {
        return this.jugadores;
    }

    public int getRondaActual() {
        return this.rondaActual;
    }

    //cosas que movi de javafx:

    public List<Jugador> jugadoresVivos() {
        return jugadores.stream()
                .filter(Jugador::estaVivo)
                .collect(Collectors.toList());
    }

    public ResultadoVotacion ejecutarVotacion(
            Map<Jugador, Jugador> votos) {

        Votacion votacion = new Votacion();

        for (Map.Entry<Jugador, Jugador> entrada
                : votos.entrySet()) {

            Jugador votante = entrada.getKey();
            Jugador votado = entrada.getValue();

            if (votado != null) {
                votacion.registrarVoto(votante, votado);
            } else {
                votacion.registrarAbstencion(votante);
            }
        }

        ResultadoVotacion resultado =
                votacion.calcularResultado();

        resultado.obtenerExpulsado()
                .ifPresent(this::eliminarJugador);

        return resultado;
    }
}



