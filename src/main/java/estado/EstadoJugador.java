package estado;

import jugadores.Jugador;
import nocturno.ContextoNocturno;
import votacion.Votacion;

public abstract class EstadoJugador {

    public abstract void realizarAccionNocturna(
            Jugador jugador,
            ContextoNocturno contexto,
            Jugador objetivo
    );

    public abstract void votarEn(
            Jugador jugador,
            Votacion votacion
    );

    public abstract void morir(
            Jugador jugador
    );

    public abstract boolean estaVivo();
}