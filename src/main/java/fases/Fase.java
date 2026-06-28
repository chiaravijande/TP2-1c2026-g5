/*package fases;

import partida.Partida;

public abstract class Fase {
    public abstract void ejecutar(Partida partida);
    public abstract Fase siguienteFase();
}*/
package fases;

import partida.Partida;

public abstract class Fase {

    public abstract void ejecutar(Partida partida);

    public abstract Fase siguienteFase(
            Partida partida
    );

    public boolean iniciaNuevaRonda() {
        return false;
    }//todavia no se inicia nueva ronda, la arranca fasediurna
}