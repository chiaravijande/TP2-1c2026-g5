package nocturno;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistroNocturno {

    private List<AccionNocturna> acciones;

    private AtaqueNocturno ataque;
    private ProteccionNocturna proteccion;
    private List<ResultadoInvestigacion> investigaciones;

    public RegistroNocturno() {

        acciones = new ArrayList<>();

        investigaciones = new ArrayList<>();
    }

    public void agregarAccion(
            AccionNocturna accion
    ) {
        acciones.add(accion);
    }

    public void ejecutarAcciones() {

        for (AccionNocturna accion : acciones) {
            accion.ejecutar(this);
        }
    }

    public void registrarAtaque(
            AtaqueNocturno ataque
    ) {
        this.ataque = ataque;
    }

    public void registrarProteccion(
            ProteccionNocturna proteccion
    ) {
        this.proteccion = proteccion;
    }

    public void registrarInvestigacion(
            ResultadoInvestigacion inv
    ) {
        investigaciones.add(inv);
    }

    public ResultadoNocturno generarResultado() {

        if (ataque == null) {
            return new ResultadoNocturno(
                    Optional.empty()
            );
        }

        boolean ataqueExitoso =
                ataque.resolverCon(
                        proteccion
                );

        if (ataqueExitoso) {
            return new ResultadoNocturno(
                    Optional.of(
                            ataque.getVictima()
                    )
            );
        }

        return new ResultadoNocturno(
                Optional.empty()
        );
    }
}