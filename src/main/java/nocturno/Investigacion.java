package nocturno;

import jugadores.Jugador;

public class Investigacion {

    private Jugador detective;
    private Jugador investigado;

    public Investigacion(
            Jugador detective,
            Jugador investigado
    ) {
        this.detective = detective;
        this.investigado = investigado;
    }

    public String resultado() {
        return investigado.getRol().bando();
    }
}
