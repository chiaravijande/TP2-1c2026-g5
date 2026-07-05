package nocturno;

import jugadores.Jugador;

public class VotoMafia implements AccionNocturna {

    private Jugador votante;
    private Jugador objetivo;

    public VotoMafia(Jugador votante, Jugador objetivo) {
        this.votante = votante;
        this.objetivo = objetivo;
    }

    @Override
    public void ejecutar(RegistroNocturno registro) {
        registro.registrarVotoMafia(votante, objetivo);
    }
}
