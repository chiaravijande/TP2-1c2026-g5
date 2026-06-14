package roles;

import jugadores.Jugador;
import partida.ContadorDeBandos;

public abstract class Rol {

    protected Jugador objetivo;

    // el jugador (la interfaz/consola) le asigne a quién va a apuntar en su turno
    public void setObjetivo(Jugador objetivo) {
        this.objetivo = objetivo;
    }

    //cada rol sabe a que contador llamar
    public abstract void agruparseEn(ContadorDeBandos contador);

    //como lo ve el detective
    public abstract boolean esSospechoso();


    //valor del enum correspondiente.
    public abstract Bando bando();
}