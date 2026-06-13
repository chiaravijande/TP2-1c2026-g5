package nocturno;

import jugadores.Jugador;
import java.util.Optional;

public class ResultadoNocturno {

    private Optional<Jugador> victima;

    public ResultadoNocturno(Optional<Jugador> victima) {
        this.victima = victima;
    }

    public Optional<Jugador> obtenerVictima() {
        return this.victima;
    }

    public boolean huboVictima() {
        return this.victima.isPresent();
    }
}
