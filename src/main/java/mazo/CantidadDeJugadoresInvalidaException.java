package mazo;

public class CantidadDeJugadoresInvalidaException extends RuntimeException {

    public CantidadDeJugadoresInvalidaException(int cantJugadores) {
        super("La cantidad de jugadores debe ser entre 5 y 12. Se recibió: " + cantJugadores);
    }
}
