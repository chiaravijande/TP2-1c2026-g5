package partida;

import jugadores.Jugador;
import fases.Fase;
import mazo.Mazo;

import java.util.List;

public class Partida {

    private int rondaActual;
    private List<Jugador> jugadores;
    private Fase faseActual;
    private Mazo mazo;

    //el constructor inicializa la partida con los jugadores, el mazo configurado y la primera fase
    public Partida(List<Jugador> jugadores, Mazo mazo, Fase faseInicial) {
        this.jugadores = jugadores;
        this.mazo = mazo;
        this.faseActual = faseInicial;
        this.rondaActual = 1;
    }

    //ordena al mazo asignar roles
    public void iniciarPartida() {
        this.mazo.repartir(this.jugadores);
    }

    //ejecuta la fase actual y luego le pide que avance a la siguiente
    public void avanzar() {
        this.faseActual.ejecutar(this);
        this.faseActual = this.faseActual.siguienteFase();

        //ver: podriamos incrementar la ronda si la nueva fase es una FaseDiurna, y no siempre que se cambie. (ver logica de incremento de fases).
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
}



