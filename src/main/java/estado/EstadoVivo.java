package estado;

import jugadores.Jugador;
import nocturno.RegistroNocturno;
import partida.Partida;
import partida.ContadorDeBandos;
import votacion.Votacion;
import roles.RolNocturno;
import roles.RolDiurno;

import java.util.List;

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
    @Override
    public void votarEnBallotage(Jugador jugador, Votacion votacion, List<Jugador> candidatos) {
        //, acá se le pediría al usuario que elija uno de los jugadores dentro de la lista 'candidatos'.

        //la interfaz nos pasa un 'jugadorElegido'.La validacion que debemos hacer seria:

        /* Jugador jugadorElegido = ... // (viene de la interfaz grafica)

        if (candidatos.contains(jugadorElegido)) {
            votacion.registrarVoto(jugador, jugadorElegido);
        } else {
            // Si intenta votar a alguien que no está en el ballotage, podemos tomarlo como una abstención o rechazar la acción.
            votacion.registrarAbstencion(jugador);
        }
        */
    }
}