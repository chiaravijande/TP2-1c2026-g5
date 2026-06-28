package nocturno;

import jugadores.Jugador;

public class AtaqueNocturno
        implements AccionNocturna {

    private Jugador atacante;
    private Jugador victima;

    public AtaqueNocturno(
            Jugador atacante,
            Jugador victima) {

        this.atacante = atacante;
        this.victima = victima;
    }

    @Override
    public void ejecutar(
            RegistroNocturno registro) {

        if (!victima.estaVivo()) {
            return;
        }

        if (victima.esAliadoDe(atacante)) {
            return;
        }

        registro.registrarAtaque(this);
    }

    public boolean resolverCon(
            ProteccionNocturna proteccion) {

        if (proteccion == null) {
            return true;
        }

        return !proteccion.protege(this);
    }

    public Jugador getVictima() {
        return victima;
    }
}