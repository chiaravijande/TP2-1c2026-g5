package roles;

import jugadores.Jugador;
import nocturno.ContextoNocturno;

public abstract class Rol {

    public abstract void realizarAccion(
            ContextoNocturno contexto,
            Jugador actor,
            Jugador objetivo
    );

    public abstract Bando bando();

    public Bando aparienciaParaDetective() {
        return bando();
    }
}
