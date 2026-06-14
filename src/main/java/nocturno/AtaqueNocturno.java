package nocturno;

import jugadores.Jugador;

public class AtaqueNocturno {

    private Jugador atacante;
    private Jugador victima;

    public AtaqueNocturno(Jugador atacante, Jugador victima) {
        this.atacante = atacante;
        this.victima = victima;
    }

    //devuelve un booleano de si la proteccion fue o no efectiva
    public boolean resolverCon(ProteccionNocturna proteccion) {
        //si no hubo medico el ataque es si o si exitoso
        if (proteccion == null) {
            return true;
        }

        //si hubo proteccion le preguntamos a la misma si freno el ataque
        return !proteccion.protege(this);
    }

    public Jugador getVictima() {
        return this.victima;
    }
}