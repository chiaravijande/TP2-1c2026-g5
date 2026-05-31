package nocturno;

import jugadores.Jugador;

public class ProteccionNocturna {

    private Jugador actor;
    private Jugador protegido;

    public ProteccionNocturna(
            Jugador actor,
            Jugador protegido
    ) {
        this.actor = actor;
        this.protegido = protegido;
    }

    public boolean protegeA(Jugador jugador) {
        return protegido.equals(jugador);
    }
}