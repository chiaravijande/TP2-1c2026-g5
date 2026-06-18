package estado;

import jugadores.Jugador;
import nocturno.RegistroNocturno;
import partida.Partida;
import partida.ContadorDeBandos;
import votacion.Votacion;

import java.util.List;

public class EstadoMuerto extends EstadoJugador {

    @Override
    public void ejecutarTurnoNocturno(Jugador jugador, RegistroNocturno contexto) {
        // No hace nada. Los muertos no atacan ni investigan.
    }

    @Override
    public void ejecutarTurnoDiurno(Jugador jugador, Partida partida) {
        // No hace nada.
    }

    @Override
    public void votarEn(Jugador jugador, Votacion votacion) {
        // No hace nada. Los muertos no votan.
    }

    @Override
    public void agruparseEn(Jugador jugador, ContadorDeBandos contador) {
        // No hace nada. Los muertos ya no se cuentan para definir si gana la Mafia o el Pueblo.
    }

    @Override
    public void eliminar(Jugador jugador) {
        // No hace nada. Ya está muerto.
    }
    @Override
    public void votarEnBallotage(Jugador jugador, Votacion votacion, List<Jugador> candidatos) {
        //no hace nada. los jugadores muertos no participan.
    }
}