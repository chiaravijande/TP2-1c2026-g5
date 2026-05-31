package votacion;

import jugadores.Jugador;
import partida.Partida;

public class ResultadoVotacion {

    private Jugador expulsado;

    public ResultadoVotacion(Jugador expulsado) {
        this.expulsado = expulsado;
    }

    public void aplicar(Partida partida) {
        expulsado.morir();
    }
}
