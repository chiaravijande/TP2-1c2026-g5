package roles.ciudadanos;

import nocturno.ResultadoInvestigacion;
import partida.ContadorDeBandos;

import java.util.Optional;

public class Sheriff extends Investigador {

    private boolean yaSeRevelo;
    private boolean quiereRevelarse;

    public Sheriff() {
        yaSeRevelo = false;
        quiereRevelarse = false;
    }

    @Override
    public String nombre() {
        return "Sheriff";
    }

    @Override
    public void agruparseEn(ContadorDeBandos contador) {
        contador.contarCiudadano();
    }

    @Override
    public boolean esSospechoso() {
        return false;
    }

    public boolean estaRevelado() {
        return yaSeRevelo;
    }

    @Override
    public boolean revelaInformacion() {
        return yaSeRevelo;
    }

    @Override
    public boolean puedeRevelarInvestigacion() {
        return !yaSeRevelo &&
                ultimaInvestigacion().isPresent();
    }

    public void decidirRevelarse() {
        quiereRevelarse = true;
    }

    public boolean quiereRevelarse() {
        return quiereRevelarse;
    }
    
    @Override
    public Optional<ResultadoInvestigacion> revelarInvestigacion() {

        if (!quiereRevelarse || yaSeRevelo) {
            return Optional.empty();
        }

        yaSeRevelo = true;
        quiereRevelarse = false;

        return ultimaInvestigacion();
    }
}