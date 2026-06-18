package roles.ciudadanos;

import jugadores.Jugador;
import nocturno.RegistroNocturno;
import nocturno.ResultadoInvestigacion;
import partida.ContadorDeBandos;
import roles.RolCiudadano;
import roles.RolNocturno;

public class Detective extends RolCiudadano implements RolNocturno {

    private Jugador ultimoInvestigado;

    @Override
    public void ejecutarAccionNocturna(RegistroNocturno contexto) {
        if (this.objetivo != null) {

            if (this.objetivo.equals(this.ultimoInvestigado)) {
                return; 
            }

            boolean esSospechoso = this.objetivo.getRol().esSospechoso();

            ResultadoInvestigacion investigacion = new ResultadoInvestigacion(this.objetivo, !esSospechoso);

            contexto.registrarInvestigacion(investigacion);

            this.ultimoInvestigado = this.objetivo;
        }
    }

    @Override
    public void agruparseEn(ContadorDeBandos contador) {
        contador.contarCiudadano();
    }

    @Override
    public boolean esSospechoso() {
        return false;
    }
}