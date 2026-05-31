package nocturno;

import jugadores.Jugador;

public class AtaqueNocturno {

    private Jugador actor;
    private Jugador victima;

    public AtaqueNocturno(
            Jugador actor,
            Jugador victima
    ) {
        this.actor = actor;
        this.victima = victima;
    }

    public Jugador getVictima() {
        return victima;
    }
}
