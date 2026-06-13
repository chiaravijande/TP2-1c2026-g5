package estado;

import jugadores.Jugador;
import nocturno.RegistroNocturno;
import partida.Partida;
import partida.ContadorDeBandos;
import votacion.Votacion;
import roles.RolNocturno;
import roles.RolDiurno;

public class EstadoVivo extends EstadoJugador {

    @Override
    public void ejecutarTurnoNocturno(Jugador jugador, RegistroNocturno contexto) {
        // Solo actúa de noche si su rol implementa la interfaz RolNocturno
        if (jugador.getRol() instanceof RolNocturno) {
            ((RolNocturno) jugador.getRol()).ejecutarAccionNocturna(contexto);
        }
    }

    @Override
    public void ejecutarTurnoDiurno(Jugador jugador, Partida partida) {
        // Solo actúa con poder especial de día si su rol implementa RolDiurno (como el Sheriff)
        if (jugador.getRol() instanceof RolDiurno) {
            ((RolDiurno) jugador.getRol()).ejecutarAccionDiurna(partida);
        }
    }

    @Override
    public void votarEn(Jugador jugador, Votacion votacion) {
        // Un jugador vivo vota normalmente.
        // (La lógica de a quién vota depende de cómo armen la Votación en su TP)
    }

    @Override
    public void agruparseEn(Jugador jugador, ContadorDeBandos contador) {
        //le dice a su rol que se sume al contador
        jugador.getRol().agruparseEn(contador);
    }

    @Override
    public void eliminar(Jugador jugador) {
        //el jugador deja de estar vivo y pasa a estar muerto.
        // Requiere que Jugador tenga el metodo cambiarEstado().
        jugador.cambiarEstado(new EstadoMuerto());
    }
}
