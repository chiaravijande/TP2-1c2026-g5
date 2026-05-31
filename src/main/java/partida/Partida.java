package partida;

import fases.Fase;
import jugadores.Jugador;

import java.util.List;

public class Partida {

    private List<Jugador> jugadores;
    private Fase faseActual;
    private int rondaActual;

    public Partida(List<Jugador> jugadores, Fase faseInicial) {
        this.jugadores = jugadores;
        this.faseActual = faseInicial;
        this.rondaActual = 1;
    }

    public void ejecutarFaseActual() {
        faseActual.ejecutar(this);
        faseActual = faseActual.siguienteFase();
    }

    public void iniciarNuevaRonda() {
        rondaActual++;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public int getRondaActual() {
        return rondaActual;
    }
}
