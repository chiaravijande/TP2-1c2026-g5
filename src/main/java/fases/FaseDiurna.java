package fases;

import partida.Partida;
import partida.ResultadoPartida;
import jugadores.Jugador;
import votacion.Votacion;
import votacion.ResultadoVotacion;
//import votacion.MecanismoEmpate;
//import votacion.MecanismoSinEliminacion; //o el mecanismo por defecto que usemos
import java.util.Optional;

public class FaseDiurna extends Fase {

    //private MecanismoEmpate mecanismoEmpate;

    //public FaseDiurna() {
    //    //mecanismo por defecto para resolver empates
    //    this.mecanismoEmpate = new MecanismoSinEliminacion();
    //}

    @Override
    public void ejecutar(Partida partida) {

        //1-acciones especiales de día (actua el sheriff)
        for (Jugador jugador : partida.getJugadores()) {
            jugador.ejecutarTurnoDiurno(partida);
        }

        //2-control de victoria: si la acción del Sheriff terminó el juego no debe haber votacion
        ResultadoPartida estadoActual = partida.verificarVictoria();
        if (estadoActual.esTerminal()) {
            return; // Cortamos la ejecución. ¡No hay necesidad de votar!
        }

        //3-comienza la votacion
        Votacion votacion = new Votacion();

        //todos los jugadores vivos emiten su voto
        for (Jugador jugador : partida.getJugadores()) {
            jugador.votarEn(votacion);
        }

        //4-calculamos el resultado de la votacion y obtenemos el optional que puede tener o no un expulsado.
        ResultadoVotacion resultado = votacion.calcularResultado();
        Optional<Jugador> expulsado = resultado.obtenerExpulsado();

        //si la Votación devolvio un Optional vacío (hubo empate o todos se abstuvieron)
        if (expulsado.isEmpty()) {
            // Aca actuaria el mecanismo de empate
            // usando MecanismoSinEliminacion  no hacemos nada
        }

        //5-eliminar al jugador mas votado si lo hubo (si el optional contiene un expulsado)
        if (expulsado.isPresent()) {
            partida.eliminarJugador(expulsado.get());
        }
    }

    @Override
    public Fase siguienteFase() {
        //al terminar el día se pasa FaseNocturna noche.
        return new FaseNocturna();
    }
}
