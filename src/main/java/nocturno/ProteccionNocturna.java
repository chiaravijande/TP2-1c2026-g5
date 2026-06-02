package nocturno;

import jugadores.Jugador;

public class ProteccionNocturna {

    private Jugador protector;
    private Jugador protegido;

    public ProteccionNocturna(
            Jugador protector,
            Jugador protegido
    ) {
        this.protector = protector;
        this.protegido = protegido;
    }

    public ResultadoNocturno resolverAtaque(
            AtaqueNocturno ataque
    ) {

        if (ataque.getVictima().equals(protegido)) {
            return new ResultadoSinVictima();
        }

        return ataque.serResueltoSinProteccion();
    }

    public Jugador getProtector() {
        return protector;
    }

    public Jugador getProtegido() {
        return protegido;
    }
}



