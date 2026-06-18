package votacion;

import jugadores.Jugador;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MecanismoBallotage implements MecanismoEmpate {

    @Override
    public Optional<Jugador> resolver(List<Jugador> empatados, Votacion votacionOriginal) {

        //crea una nueva urna exclusiva para el desempate
        Votacion urnaDeBallotage = new Votacion();

        //obtiene participantes de la votación original
        Set<Jugador> votantes = votacionOriginal.obtenerVotantes();

        //le pedimos a los votantes que vuelan a votar, pero solo son candidatos los "empatados" de la votacion original
        for (Jugador votante : votantes) {

            votante.votarEnBallotage(urnaDeBallotage, empatados);
        }

        //calcula el nuevo resultado
        ResultadoVotacion resultadoBallotage = urnaDeBallotage.calcularResultado();

        //si vuelve a haber un empate exacto, el optional queda vacio y la fase terminara sin eliminados.
        return resultadoBallotage.obtenerExpulsado();
    }
}