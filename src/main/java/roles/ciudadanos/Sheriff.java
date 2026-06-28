package roles.ciudadanos;

import partida.Partida;
import partida.ContadorDeBandos;

public class Sheriff
        extends Investigador {

    private boolean yaSeRevelo;

    public Sheriff() {
        yaSeRevelo = false;
    }

    public boolean estaRevelado() {
        return yaSeRevelo;
    }

    @Override
    public String nombre() {
        return "Sheriff";
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
