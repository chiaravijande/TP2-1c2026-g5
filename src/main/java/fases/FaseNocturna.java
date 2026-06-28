package fases;

import nocturno.RegistroNocturno;
import partida.Partida;
import jugadores.Jugador;
import nocturno.RegistroNocturno;
import nocturno.ResultadoNocturno;
import java.util.Optional;

public class FaseNocturna extends Fase {

    @Override
    public void ejecutar(
            Partida partida) {

        RegistroNocturno registro =
                new RegistroNocturno();

        for (Jugador jugador :
                partida.getJugadores()) {

            jugador.ejecutarTurnoNocturno(
                    registro
            );
        }

        registro.ejecutarAcciones();

        ResultadoNocturno resultado =
                registro.generarResultado();

        resultado.obtenerVictima()
                .ifPresent(
                        partida::eliminarJugador
                );
    }

    @Override
    public Fase siguienteFase(
            Partida partida
    ) {
        return new FaseDiurna();
    }
}

