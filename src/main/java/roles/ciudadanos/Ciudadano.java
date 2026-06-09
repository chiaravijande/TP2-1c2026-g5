package roles.ciudadanos;

import jugadores.Jugador;
import nocturno.ContextoNocturno;

public class Ciudadano extends RolCiudadano {

    @Override
    public void realizarAccion(
            ContextoNocturno contexto,
            Jugador actor,
            Jugador objetivo
    ) {

        // No tiene acción nocturna

    }
}
