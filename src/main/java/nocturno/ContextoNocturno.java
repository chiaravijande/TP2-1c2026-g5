package nocturno;

import java.util.List;
import java.util.ArrayList;

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

    public ResultadoNocturno generarResultado() {

        if (proteccion != null &&
                proteccion.protegeA(ataque.getVictima())) {

            return new ResultadoSinVictima();
        }

        return new ResultadoConVictima(
                ataque.getVictima()
        );
    }
}
