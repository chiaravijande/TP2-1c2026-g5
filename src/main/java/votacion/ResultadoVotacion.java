package votacion;

import jugadores.Jugador;
import java.util.Optional;

public class ResultadoVotacion {

    private Optional<Jugador> expulsado;

    // el constructor recibe el Optional ya establecido por la clase votacion
    public ResultadoVotacion(Optional<Jugador> expulsado) {
        this.expulsado = expulsado;
    }

    public Optional<Jugador> obtenerExpulsado() {
        return this.expulsado;
    }
}