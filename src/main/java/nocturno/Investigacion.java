package nocturno;

import jugadores.Jugador;
import roles.Bando;

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

    public Bando resultado() {
        return investigado.getRol().aparienciaParaDetective();
    }
}
