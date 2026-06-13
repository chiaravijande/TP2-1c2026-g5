package fases;

import partida.Partida;

public abstract class Fase {
    public abstract void ejecutar(Partida partida);
    public abstract Fase siguienteFase();
}