package estado;

import jugadores.Jugador;
import nocturno.RegistroNocturno;
import partida.Partida;
import partida.ContadorDeBandos;
import votacion.Votacion;

import java.util.List;

public abstract class EstadoJugador {

    public abstract void ejecutarTurnoNocturno(Jugador jugador, RegistroNocturno contexto);

    public abstract void ejecutarTurnoDiurno(Jugador jugador, Partida partida);

    public abstract void votarEn(Jugador jugador, Votacion votacion);

    public abstract void agruparseEn(Jugador jugador, ContadorDeBandos contador);

    public abstract void eliminar(Jugador jugador);

    public abstract void votarEnBallotage(Jugador jugador, Votacion votacion, List<Jugador> candidatos);
}