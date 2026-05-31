package roles;

import jugadores.Jugador;
import nocturno.ContextoNocturno;

public abstract class Rol {

    public abstract void realizarAccion(
            ContextoNocturno contexto,
            Jugador actor
    );

    public abstract String bando();

    public String aparienciaParaDetective() {
        return bando();
    }
}
