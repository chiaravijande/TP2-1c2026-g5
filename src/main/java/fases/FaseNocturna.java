package fases;

import nocturno.ContextoNocturno;
import nocturno.ResultadoNocturno;
import partida.Partida;
import fases.Fase;

public class FaseNocturna extends Fase {

    private ContextoNocturno contexto;

    public FaseNocturna() {
        this.contexto = new ContextoNocturno();
    }

    @Override
    public void ejecutar(Partida partida) {

        partida.getJugadores()
                .forEach(j -> j.realizarAccionNocturna(contexto));

        ResultadoNocturno resultado = contexto.generarResultado();

        resultado.aplicar(partida);
        resultado.anunciar();
    }

    @Override
    public Fase siguienteFase() {
        return new FaseDiurna();
    }
}
