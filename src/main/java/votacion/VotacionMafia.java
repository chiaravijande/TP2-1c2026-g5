package votacion;

import jugadores.Jugador;
import java.util.List;
import java.util.Optional;

public class VotacionMafia extends Votacion {

    @Override
    public void registrarVoto(Jugador votante, Jugador votado) {
        
        if (votado != null && votado.esAliadoDe(votante)) {
            return;
        }
        super.registrarVoto(votante, votado);
    }

    @Override
    protected Optional<Jugador> resolverEmpate(List<Jugador> empatados) {

        for (Jugador votante : obtenerVotantes()) {

            if (!votante.tienePrioridadDeDesempate()) {
                continue;
            }

            Optional<Jugador> votoConPrioridad = obtenerVotoDe(votante);

            if (votoConPrioridad.isPresent() && empatados.contains(votoConPrioridad.get())) {
                return votoConPrioridad;
            }
        }

        return Optional.empty();
    }
}

