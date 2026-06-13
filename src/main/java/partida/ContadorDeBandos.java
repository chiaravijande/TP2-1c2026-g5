package partida;

public class ContadorDeBandos {

    private int cantMafiosos;
    private int cantCiudadanos;

    // Al instanciar el contador, arranca de cero.
    public ContadorDeBandos() {
        this.cantMafiosos = 0;
        this.cantCiudadanos = 0;
    }

    public void contarMafioso() {
        this.cantMafiosos++;
    }

    public void contarCiudadano() {
        this.cantCiudadanos++;
    }

    public ResultadoPartida evaluarCondicion() {

        if (this.cantMafiosos == 0) {
            return new GanaCiudadanos();
        }

        if (this.cantMafiosos >= this.cantCiudadanos) {
            return new GanaMafia();
        }

        return new PartidaEnCurso();
    }
}
