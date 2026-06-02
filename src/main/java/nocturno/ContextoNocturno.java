package nocturno;

import partida.Partida;

import java.util.ArrayList;
import java.util.List;

public class ContextoNocturno {

    private AtaqueNocturno ataque;
    private ProteccionNocturna proteccion;

    private List<Investigacion> investigaciones;

    public ContextoNocturno() {
        this.investigaciones = new ArrayList<>();
    }

    public void registrarAtaque(AtaqueNocturno ataque) {
        this.ataque = ataque;
    }

    public void registrarProteccion(
            ProteccionNocturna proteccion
    ) {
        this.proteccion = proteccion;
    }

    public void registrarInvestigacion(
            Investigacion investigacion
    ) {
        investigaciones.add(investigacion);
    }

    public void resolver(Partida partida) {

        ResultadoNocturno resultado = generarResultado();

        resultado.aplicar(partida);
        resultado.anunciar();
    }

    private ResultadoNocturno generarResultado() {
        if (ataque == null) {
            return new ResultadoSinVictima(); }
        return ataque.resolverCon(proteccion);
    }
}

