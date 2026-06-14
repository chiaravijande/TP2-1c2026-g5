package nocturno;

import jugadores.Jugador;

public class ProteccionNocturna {

    private Jugador protegido;
    private Jugador protector;

    //el constructor recibe al jugador que recibio la proteccion del medico, y el propio medico
    public ProteccionNocturna(Jugador protegido, Jugador protector) {
        this.protegido = protegido;
        this.protector = protector;
    }

    //verifica si la proteccion funciono
    public boolean protege(AtaqueNocturno ataque) {
        // Compara si el jugador atacado es el mismo que el protegido
        return ataque.getVictima().equals(this.protegido);
    }
}


