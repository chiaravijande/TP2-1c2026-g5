package nocturno;

import jugadores.Jugador;

public class ResultadoInvestigacion {

    private Jugador investigado;
    private boolean inocente;

    //el constructor recibe quien investigó el detective y el resultado de esa investigación
    public ResultadoInvestigacion(Jugador investigado, boolean inocente) {
        this.investigado = investigado;
        this.inocente = inocente;
    }

    //devuelve el resultado de la votacion,
    //"pareceInocente" porque puede no ser inocente e igualmente devolver true (caso padrino)
    public boolean pareceInocente() {
        return this.inocente;
    }

}