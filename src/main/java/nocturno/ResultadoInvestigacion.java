package nocturno;

import jugadores.Jugador;

public class ResultadoInvestigacion
        implements AccionNocturna {

    private Jugador investigado;
    private boolean inocente;
    private Jugador investigador;

    public ResultadoInvestigacion(
            Jugador investigador,
            Jugador investigado) {

        this.investigador = investigador;
        this.investigado = investigado;
        this.inocente = !investigado.esSospechoso();
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

    public Jugador investigado() {
        return investigado;
    }

    public Jugador investigador() {
        return investigador;
    }

    public String descripcion() {

        if (pareceInocente()) {
            return investigado.getNombre() + " parece inocente.";
        }

        return investigado.getNombre() + " pertenece a la mafia.";
    }
}