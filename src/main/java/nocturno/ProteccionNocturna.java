package nocturno;

import jugadores.Jugador;

public class ProteccionNocturna
        implements AccionNocturna {

    private Jugador protegido;

    public ProteccionNocturna(
            Jugador protegido) {

        this.protegido = protegido;
    }

    @Override
    public void ejecutar(
            RegistroNocturno registro) {

        if (!protegido.estaVivo()) {
            return;
        }

        registro.registrarProteccion(this);
    }

    public boolean protege(
            AtaqueNocturno ataque) {

        return protegido ==
                ataque.getVictima();
    }
}


