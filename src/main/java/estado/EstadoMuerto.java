package estado;

import jugadores.Jugador;
import nocturno.RegistroNocturno;
import partida.ContadorDeBandos;
import partida.Partida;
import votacion.Votacion;

import java.util.List;

public class EstadoMuerto extends EstadoJugador {

    @Override
    public void ejecutarTurnoNocturno(
            Jugador jugador,
            RegistroNocturno contexto
    ) {
    }

    @Override
    public void ejecutarTurnoDiurno(
            Jugador jugador,
            Partida partida
    ) {
    }

    @Override
    public void votarEn(
            Jugador jugador,
            Votacion votacion
    ) {
    }

    @Override
    public void votarEnBallotage(
            Jugador jugador,
            Votacion votacion,
            List<Jugador> candidatos
    ) {
    }

    @Override
    public void agruparseEn(
            Jugador jugador,
            ContadorDeBandos contador
    ) {
    }

    @Override
    public void eliminar(
            Jugador jugador
    ) {
    }

    @Override
    public boolean estaVivo() {
        return false;
    }
}