package partida;

public abstract class ResultadoPartida {
    //mensaje que se mostrara
    public abstract String anunciar();

    //indica si el juego termino o no
    public abstract boolean esTerminal();
}
