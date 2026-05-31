package roles.ciudadanos;

import jugadores.Jugador;
import nocturno.ContextoNocturno;
import nocturno.ProteccionNocturna;

public class Medico extends RolCiudadano {

    @Override
    public void realizarAccion(
            ContextoNocturno contexto,
            Jugador actor
    ) {

        Jugador protegido = null;

        ProteccionNocturna proteccion =
                new ProteccionNocturna(
                        actor,
                        protegido
                );

        contexto.registrarProteccion(
                proteccion
        );
    }
}
