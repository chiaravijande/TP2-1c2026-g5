package votacion;

import jugadores.Jugador;
import java.util.*;

public class Votacion {

    private Map<Jugador, Jugador> votos;
    private List<Jugador> empatados;

    public Votacion() {
        this.votos = new HashMap<>();
        this.empatados = new ArrayList<>();
    }

    public void registrarVoto(Jugador votante, Jugador votado) {
        if (votado != null && !votado.estaVivo()) {
            return; 
        }
        this.votos.put(votante, votado);
    }

    public void registrarAbstencion(Jugador votante) {
        this.votos.put(votante, null);
    }


    public ResultadoVotacion calcularResultado() {
        if (this.votos.isEmpty()) return new ResultadoVotacion(Optional.empty());

        Map<Jugador, Integer> conteo = new HashMap<>();
        for (Jugador votado : this.votos.values()) {
            if (votado != null) {
                conteo.put(votado, conteo.getOrDefault(votado, 0) + 1);
            }
        }

        int maxVotos = 0;
        this.empatados.clear();

        for (Map.Entry<Jugador, Integer> entrada : conteo.entrySet()) {
            int cantidadVotos = entrada.getValue();
            Jugador candidato = entrada.getKey();

            if (cantidadVotos > maxVotos) {
                maxVotos = cantidadVotos;
                this.empatados.clear();
                this.empatados.add(candidato);
            } else if (cantidadVotos == maxVotos) {
                this.empatados.add(candidato);
            }
        }

        if (this.empatados.isEmpty()) {
            return new ResultadoVotacion(Optional.empty());
        }

        if (this.empatados.size() > 1) {
            return new ResultadoVotacion(resolverEmpate(this.empatados));
        }

        return new ResultadoVotacion(Optional.of(this.empatados.get(0)));
    }

    protected Optional<Jugador> resolverEmpate(List<Jugador> empatados) {
        return Optional.empty();
    }

    protected Optional<Jugador> obtenerVotoDe(Jugador votante) {
        return Optional.ofNullable(this.votos.get(votante));
    }

    public List<Jugador> obtenerEmpatados() {
        return this.empatados;
    }
    public Set<Jugador> obtenerVotantes() {
        return this.votos.keySet();
    }
}