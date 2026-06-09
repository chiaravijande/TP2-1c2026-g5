package roles.ciudadanos;

import jugadores.Jugador;
import nocturno.ContextoNocturno;

public class Sheriff extends RolCiudadano {

    private boolean yaSeRevelo;

    public Sheriff() {
        this.yaSeRevelo = false;
    }

    @Override
    public void realizarAccion(
            ContextoNocturno contexto,
            Jugador actor,
            Jugador objetivo
    ) {

        // No tiene acción nocturna

    }

    public void revelarInvestigacion(
            String resultado
    ) {

        if (yaSeRevelo) {

            throw new RuntimeException(
                    "El sheriff ya usó su habilidad."
            );
        }

        System.out.println(
                "Sheriff revela: " + resultado
        );

        yaSeRevelo = true;
    }
}
