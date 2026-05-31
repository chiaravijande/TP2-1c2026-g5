package roles.ciudadanos;

import jugadores.Jugador;
import nocturno.ContextoNocturno;
import nocturno.Investigacion;

public class Detective extends RolCiudadano {

    @Override
    public void realizarAccion(
            ContextoNocturno contexto,
            Jugador actor
    ) {

        Jugador investigado = null;

        Investigacion investigacion =
                new Investigacion(
                        actor,
                        investigado
                );

        contexto.registrarInvestigacion(
                investigacion
        );
    }
}
