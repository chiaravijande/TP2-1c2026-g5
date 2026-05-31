package votacion;

import jugadores.Jugador;

import java.util.HashMap;
import java.util.Map;

public class Votacion {

    private Map<Jugador, Jugador> votos;

    public Votacion() {

        votos = new HashMap<>();
    }

    public void registrarVoto(
            Jugador votante,
            Jugador votado
    ) {

        votos.put(votante, votado);
    }

    public ResultadoVotacion calcularResultado() {

        Map<Jugador, Integer> conteo =
                new HashMap<>();

        for (Jugador votado : votos.values()) {

            conteo.put(
                    votado,
                    conteo.getOrDefault(votado, 0) + 1
            );
        }

        Jugador expulsado =
                conteo.entrySet()
                        .stream()
                        .max(Map.Entry.comparingByValue())
                        .get()
                        .getKey();

        return new ResultadoVotacion(
                expulsado
        );
    }
}
