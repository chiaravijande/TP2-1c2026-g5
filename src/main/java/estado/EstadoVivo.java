package estado;

import jugadores.Jugador;
import nocturno.ContextoNocturno;
import votacion.Votacion;

public class EstadoVivo extends EstadoJugador {

    @Override
    public void realizarAccionNocturna(
            Jugador jugador,
            ContextoNocturno contexto,
            Jugador objetivo
    ) {
        jugador.getRol().realizarAccion(
                contexto,
                jugador,
                objetivo
        );
    }

    @Override
    public void votarEn(Jugador jugador, Votacion votacion) {
        Jugador votado = null;

        votacion.registrarVoto(
                jugador,
                votado
        );
    }

    @Override
    public void morir(Jugador jugador) {
        jugador.cambiarEstado(new EstadoMuerto());
    }

    @Override
    public boolean estaVivo() {
        return true;
    }
}
