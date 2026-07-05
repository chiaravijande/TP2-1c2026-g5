package estado;

import jugadores.Jugador;
import nocturno.AccionNocturna;
import nocturno.RegistroNocturno;
import partida.ContadorDeBandos;
import partida.Partida;
import votacion.Votacion;

import java.util.List;
import java.util.Optional;

public class EstadoVivo extends EstadoJugador {

    @Override
    public void ejecutarTurnoNocturno(
            Jugador jugador,
            RegistroNocturno contexto
    ) {
        jugador.prepararAccionNocturna()
                .ifPresent(
                        accion ->
                                accion.ejecutar(contexto)
                );
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
        jugador.agruparseSegunRol(contador);
    }

    @Override
    public void eliminar(
            Jugador jugador
    ) {
        jugador.cambiarEstado(
                new EstadoMuerto()
        );
    }

    @Override
    public boolean estaVivo() {
        return true;
    }
}