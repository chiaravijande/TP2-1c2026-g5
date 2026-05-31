package roles.mafia;

import jugadores.Jugador;
import nocturno.AtaqueNocturno;
import nocturno.ContextoNocturno;

public class Padrino extends RolMafia {

    @Override
    public void realizarAccion(
            ContextoNocturno contexto,
            Jugador actor
    ) {

        Jugador victima = null;

        AtaqueNocturno ataque =
                new AtaqueNocturno(
                        actor,
                        victima
                );

        contexto.registrarAtaque(ataque);
    }

    @Override
    public String aparienciaParaDetective() {

        // El detective lo ve como ciudadano

        return "Ciudadano";
    }
}
