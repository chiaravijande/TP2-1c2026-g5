package nocturno;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistroNocturno {

    //puede haber 0 o 1 ataque, 0 o 1 proteccon y muchas investigaciones (depende de cantidad de jugadores)
    private AtaqueNocturno ataque;
    private ProteccionNocturna proteccion;
    private List<ResultadoInvestigacion> investigaciones;

    public RegistroNocturno() {
        this.investigaciones = new ArrayList<>();
    }

    public void registrarAtaque(AtaqueNocturno ataque) {
        this.ataque = ataque;
    }

    public void registrarProteccion(ProteccionNocturna proteccion) {
        this.proteccion = proteccion;
    }

    public void registrarInvestigacion(ResultadoInvestigacion inv) {
        this.investigaciones.add(inv);
    }

    //evalua lo que paso en la noche y determina un resultado
    public ResultadoNocturno generarResultado() {

        if (this.ataque == null) {
            return new ResultadoNocturno(Optional.empty());
        }

        //el ataque se resuelve contra la protección y devuelve un boolean
        boolean ataqueExitoso = this.ataque.resolverCon(this.proteccion);

        if (ataqueExitoso) {
            return new ResultadoNocturno(
                    Optional.of(this.ataque.getVictima())
            );
        } else {
            return new ResultadoNocturno(Optional.empty());
        }
    }
}