
package nocturno;

import jugadores.Jugador;
import votacion.ResultadoVotacion;
import votacion.VotacionMafia;

import java.util.ArrayList;
import java.util.List;

public class RegistroNocturno {

    private List<AccionNocturna> acciones;

    private AtaqueNocturno ataque;
    private ProteccionNocturna proteccion;
    private List<ResultadoInvestigacion> investigaciones;
    private VotacionMafia votacionMafia;

    public RegistroNocturno() {
        acciones = new ArrayList<>();
        investigaciones = new ArrayList<>();
        votacionMafia = new VotacionMafia();
    }

    public void agregarAccion(AccionNocturna accion) {
        acciones.add(accion);
    }

    public void ejecutarAcciones() {
        for (AccionNocturna accion : acciones) {
            accion.ejecutar(this);
        }
    }

    public void registrarVotoMafia(Jugador votante, Jugador objetivo) {
        votacionMafia.registrarVoto(votante, objetivo);
    }

    //se llama una vez que todos los jugadores tuvieron su turno nocturno:
    //convierte los votos acumulados en, a lo sumo, un único AtaqueNocturno.
    public void resolverAtaqueMafia() {
        ResultadoVotacion resultado = votacionMafia.calcularResultado();
        resultado.obtenerExpulsado()
                .ifPresent(victima -> new AtaqueNocturno(victima).ejecutar(this));
    }

    public void registrarAtaque(AtaqueNocturno ataque) {
        this.ataque = ataque;
    }

    public void registrarProteccion(ProteccionNocturna proteccion) {
        this.proteccion = proteccion;
    }

    public void registrarInvestigacion(ResultadoInvestigacion inv) {
        investigaciones.add(inv);
    }

    public ResultadoNocturno generarResultado() {
        if (ataque == null) {
            return new ResultadoNocturno(java.util.Optional.empty());
        }

        boolean ataqueExitoso = ataque.resolverCon(proteccion);

        if (ataqueExitoso) {
            return new ResultadoNocturno(java.util.Optional.of(ataque.getVictima()));
        }

        return new ResultadoNocturno(java.util.Optional.empty());
    }
}