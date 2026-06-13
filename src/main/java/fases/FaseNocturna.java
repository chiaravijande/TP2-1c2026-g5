package fases;

import nocturno.RegistroNocturno;
import partida.Partida;
import jugadores.Jugador;
import nocturno.RegistroNocturno;
import nocturno.ResultadoNocturno;
import java.util.Optional;

public class FaseNocturna extends Fase {

    @Override
    public void ejecutar(Partida partida) {
        // 1-preparacion del contexto
        RegistroNocturno contexto = new RegistroNocturno();

        // 2-recolección de acciones:jugadores ejecutan su turno.
        for (Jugador jugador : partida.getJugadores()) {
            jugador.ejecutarTurnoNocturno(contexto);
        }

        // 3-resolución de la noche
        ResultadoNocturno resultado = contexto.generarResultado();
        Optional<Jugador> posibleVictima = resultado.obtenerVictima();

        // 4-aplicar consecuencias (si hubo un asesinato exitoso y no lo curaron)
        if (posibleVictima.isPresent()) {
            partida.eliminarJugador(posibleVictima.get());
        }
    }

    @Override
    public Fase siguienteFase() {
        // cuando termina la faseNocturna siempre se cambia de fase
        return new FaseDiurna();
    }
}

