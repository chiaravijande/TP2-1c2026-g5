package nocturno;

import jugadores.Jugador;
import partida.Partida;

public class ResultadoConVictima
        extends ResultadoNocturno {

    private Jugador victima;

    public ResultadoConVictima(Jugador victima) {
        this.victima = victima;
    }

    @Override
    public void aplicar(Partida partida) {
        victima.morir();
    }

    @Override
    public void anunciar() {
        System.out.println(
                victima.getNombre() + " fue eliminado."
        );
    }
}
