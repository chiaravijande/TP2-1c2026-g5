package roles;

import jugadores.Jugador;
import nocturno.AccionNocturna;

import java.util.Optional;

public interface RolNocturno {

    Optional<AccionNocturna> prepararAccion(
            Jugador actor,
            Optional<Jugador> objetivo
    );
}