package roles.ciudadanos;

import jugadores.Jugador;
import nocturno.RegistroNocturno;
import nocturno.ResultadoInvestigacion;
import partida.ContadorDeBandos;
import roles.RolCiudadano;
import roles.RolNocturno;

public class Detective extends Investigador {

    @Override
    public String nombre() {
        return "Detective";
    }

    @Override
    public void agruparseEn(
            ContadorDeBandos contador) {

        contador.contarCiudadano();
    }

    @Override
    public boolean esSospechoso() {
        return false;
    }
}
