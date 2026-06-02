package nocturno;

import jugadores.Jugador;

public class AtaqueNocturno {

    private Jugador atacante;
    private Jugador victima;

    public AtaqueNocturno(
            Jugador atacante,
            Jugador victima
    ) {
        this.atacante = atacante;
        this.victima = victima;
    }

    public Jugador getVictima() {
        return victima;
    }

    public Jugador getAtacante() {
        return atacante;
    }

    public ResultadoNocturno resolverCon(
            ProteccionNocturna proteccion
    ) {

        if (proteccion == null) {
            return serResueltoSinProteccion();
        }

        return proteccion.resolverAtaque(this);
    }

    public ResultadoNocturno serResueltoSinProteccion() {
        return new ResultadoConVictima(victima);
    }
}




