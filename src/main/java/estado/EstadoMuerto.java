package estado;

import jugadores.Jugador;
import nocturno.ContextoNocturno;
import votacion.Votacion;

public class EstadoMuerto extends EstadoJugador {

    @Override
    public void realizarAccionNocturna(
            Jugador jugador,
            ContextoNocturno contexto,
            Jugador objetivo
    ) {
        // no hace nada
    }

    @Override
    public void votarEn(Jugador jugador, Votacion votacion) {
        // no vota
    }

    @Override
    public void morir(Jugador jugador) {
        // ya está muerto
    }

    @Override
    public boolean estaVivo() {
        return false;
    }
}
