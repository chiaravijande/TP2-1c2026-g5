package partida;

public class GanaMafia extends ResultadoPartida {

    @Override
    public String anunciar() {
        return "La Mafia controlo el pueblo y gana la partida";
    }

    @Override
    public boolean esTerminal() {
        //como hubo ganador el juego debe terminar.
        return true;
    }
}

