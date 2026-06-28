package fases;

import partida.Partida;
import partida.ResultadoPartida;

public class FaseFinal extends Fase {

    private ResultadoPartida resultado;

    public FaseFinal(
            ResultadoPartida resultado
    ) {
        this.resultado = resultado;
    }

    @Override
    public void ejecutar(
            Partida partida
    ) {
        // no hace nada
    }

    @Override
    public Fase siguienteFase(
            Partida partida
    ) {
        return this;
    }

    public ResultadoPartida obtenerResultado() {
        return resultado;
    }
}