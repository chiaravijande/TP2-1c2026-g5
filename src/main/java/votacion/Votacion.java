package votacion;

import jugadores.Jugador;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Votacion {

    //guarda quien vota a quien
    private Map<Jugador, Jugador> votos;

    public Votacion() {
        this.votos = new HashMap<>();
    }

    public void registrarVoto(Jugador votante, Jugador votado) {
        this.votos.put(votante, votado);
    }


    public void registrarAbstencion(Jugador votante) {
        this.votos.put(votante, null);
    }

    //determina quien tiene mas votos. si hay un empate devuelve un optional vacio (para que actue el mecanismo de empate)
    public ResultadoVotacion calcularResultado() {
        if (this.votos.isEmpty()) {
            return new ResultadoVotacion(Optional.empty());
        }
        //contamos votos por jugador
        Map<Jugador, Integer> conteo = new HashMap<>();

        for (Jugador votado : this.votos.values()) {
            if (votado != null) { // Ignoramos las abstenciones
                conteo.put(votado, conteo.getOrDefault(votado, 0) + 1);
            }
        }

        //buscamos el mas votado
        Jugador masVotado = null;
        int maxVotos = 0;
        boolean hayEmpate = false;

        for (Map.Entry<Jugador, Integer> entrada : conteo.entrySet()) {
            int cantidadVotos = entrada.getValue();
            Jugador candidato = entrada.getKey();

            if (cantidadVotos > maxVotos) {
                masVotado = candidato;
                maxVotos = cantidadVotos;
                hayEmpate = false; // Hay un nuevo líder claro
            } else if (cantidadVotos == maxVotos) {
                hayEmpate = true; // Alguien empató con el líder
            }
        }

        //devolvemos el resultado o si hay empate no se expulsa directamente a nadie
        if (hayEmpate || masVotado == null) {
            return new ResultadoVotacion(Optional.empty());
        }

        //si hay un ganador lo devolvemos encapsulado
        return new ResultadoVotacion(Optional.of(masVotado));
    }
}
