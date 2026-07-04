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

        //antes iba aca

        for (Jugador jugador : partida.getJugadores()) {

            jugador.ejecutarTurnoNocturno(registro);
        }

        registro.ejecutarAcciones();
        registro.resolverAtaqueMafia();

        ResultadoNocturno resultado =
                registro.generarResultado();

        resultado.obtenerVictima()
                .ifPresent(
                        partida::eliminarJugador
                );
        /* limpia los objetivos elegidos en la noche anterior antes de que arranque esta, movi este metodo
        aca a lo ultimo porque antes se borraba tod0 el registro de entrada*/
        for (Jugador jugador : partida.getJugadores()) {
            jugador.abstenerse();
        }
    }

    @Override
    public Fase siguienteFase(
            Partida partida
    ) {
        return new FaseDiurna();
    }
}

