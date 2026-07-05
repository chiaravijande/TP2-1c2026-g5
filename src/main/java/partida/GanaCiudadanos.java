package partida;

public class GanaCiudadanos extends ResultadoPartida {

    @Override
    public String anunciar() {
        return "Los Ciudadanos eliminaron a la Mafia y ganan la partida";
    }

    @Override
    public boolean esTerminal() {
        //como hubo ganador el juego debe terminar.
        return true;
    }
}
