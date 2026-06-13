package roles.ciudadanos;

import jugadores.Jugador;
import nocturno.RegistroNocturno;
import nocturno.ResultadoInvestigacion;
import partida.ContadorDeBandos;

public class Detective extends roles.RolCiudadano implements roles.RolNocturno {

    private Jugador ultimoInvestigado;

    @Override
    public void ejecutarAccionNocturna(RegistroNocturno contexto) {
        if (this.objetivo != null) {
            // Le pregunta al rol del objetivo si es sospechoso (El Padrino mentirá, el Mafioso dirá la verdad)
            boolean esSospechoso = this.objetivo.getRol().esSospechoso();

            // Creamos el resultado de la investigación (invirtiendo el booleano porque la clase pide 'inocente')
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