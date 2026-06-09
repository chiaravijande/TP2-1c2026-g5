package roles.ciudadanos;

import jugadores.Jugador;
import nocturno.ContextoNocturno;
import nocturno.Investigacion;

public class Detective extends RolCiudadano {

    @Override
    public void realizarAccion(
            ContextoNocturno contexto,
            Jugador actor,
            Jugador objetivo
    ) {

        Investigacion investigacion =
                new Investigacion(
                        actor,
                        objetivo
                );

        contexto.registrarInvestigacion(
                investigacion
        );
    }
}