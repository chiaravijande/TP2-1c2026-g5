package roles.mafia;

import jugadores.Jugador;
import nocturno.AtaqueNocturno;
import nocturno.ContextoNocturno;
import roles.Bando;
import roles.Rol;

public class Mafioso extends Rol {

    @Override
    public void realizarAccion(
            ContextoNocturno contexto,
            Jugador actor,
            Jugador objetivo
    ) {

        AtaqueNocturno ataque =
                new AtaqueNocturno(
                        actor,
                        objetivo
                );

        contexto.registrarAtaque(
                ataque
        );
    }

    @Override
    public Bando bando() {

        return Bando.MAFIA;
    }
}