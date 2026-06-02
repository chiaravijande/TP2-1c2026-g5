package fases;

import nocturno.ContextoNocturno;
import partida.Partida;

public class FaseNocturna extends Fase {

    private ContextoNocturno contexto;

    public FaseNocturna() {
        this.contexto = new ContextoNocturno();
    }

    @Override
    public void ejecutar(Partida partida) {

        partida.getJugadores()
                .forEach(j -> j.realizarAccionNocturna(contexto));

        contexto.resolver(partida);
    }

    @Override
    public Fase siguienteFase() {
        return new FaseDiurna();
    }
}

