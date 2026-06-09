package roles.ciudadanos;

import jugadores.Jugador;
import nocturno.ContextoNocturno;
import nocturno.ProteccionNocturna;

public class Medico extends RolCiudadano {

    @Override
    public void realizarAccion(
            ContextoNocturno contexto,
            Jugador actor,
            Jugador objetivo
    ) {

        ProteccionNocturna proteccion =
                new ProteccionNocturna(
                        actor,
                        objetivo
                );

        contexto.registrarProteccion(
                proteccion
        );
    }
}
