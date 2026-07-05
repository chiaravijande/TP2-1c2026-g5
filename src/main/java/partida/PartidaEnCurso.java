package partida;

public class PartidaEnCurso extends ResultadoPartida {

    @Override
    public String anunciar() {
        return "Nadie gano aun. La partida continua";
    }

    @Override
    public boolean esTerminal() {
        //como el juego sigue,NO es un estado terminal.
        return false;
    }
}
