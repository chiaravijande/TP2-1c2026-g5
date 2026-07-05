package roles.ciudadanos;

import jugadores.Jugador;
import nocturno.AccionNocturna;
import nocturno.ResultadoInvestigacion;
import roles.RolCiudadano;

import java.util.Optional;

public abstract class Investigador
        extends RolCiudadano {

    private Jugador ultimoInvestigado;
    private Optional<ResultadoInvestigacion> ultimaInvestigacion =
            Optional.empty();

    @Override
    public Optional<AccionNocturna> prepararAccion(
            Jugador actor,
            Optional<Jugador> objetivo) {

        return objetivo.flatMap(jugador -> {

            if (jugador == ultimoInvestigado) {
                return Optional.empty();
            }

            ultimoInvestigado = jugador;

            ResultadoInvestigacion resultado =
                    new ResultadoInvestigacion(actor, jugador);

            ultimaInvestigacion =
                    Optional.of(resultado);

            return Optional.of(resultado);
        });
    }

    @Override
    public Optional<ResultadoInvestigacion> revelarInvestigacion() {
        return Optional.empty();
    }

    protected Optional<ResultadoInvestigacion> ultimaInvestigacion() {
        return ultimaInvestigacion;
    }
}

