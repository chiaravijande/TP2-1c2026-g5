package fases;

import partida.Partida;
import votacion.ResultadoVotacion;
import votacion.Votacion;
import fases.Fase;

public class FaseDiurna extends Fase {

    private Votacion votacion;

    public FaseDiurna() {
        this.votacion = new Votacion();
    }

    @Override
    public void ejecutar(Partida partida) {

        ResultadoVotacion resultado = votacion.calcularResultado();

        resultado.aplicar(partida);
    }

    @Override
    public Fase siguienteFase() {
        return new FaseNocturna();
    }
}
