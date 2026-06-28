package nocturno;

import jugadores.Jugador;

public class ResultadoInvestigacion
        implements AccionNocturna {

    private Jugador investigado;
    private boolean inocente;
    private Jugador investigador;

    /*public ResultadoInvestigacion(
            Jugador investigado
    ) {
        this.investigado = investigado;
        this.inocente =
                !investigado.esSospechoso();
    }*/
    public ResultadoInvestigacion(
            Jugador investigador,
            Jugador investigado) {

        this.investigador = investigador;
        this.investigado = investigado;
        this.inocente =
                !investigado.esSospechoso();
    }

    @Override
    public void ejecutar(
            RegistroNocturno registro
    ) {
        registro.registrarInvestigacion(this);
    }

    public boolean pareceInocente() {
        return inocente;
    }
}