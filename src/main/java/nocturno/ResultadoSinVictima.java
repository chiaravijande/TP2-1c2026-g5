package nocturno;

import partida.Partida;

public class ResultadoSinVictima
        extends ResultadoNocturno {

    @Override
    public void aplicar(Partida partida) {
    }

    @Override
    public void anunciar() {
        System.out.println(
                "Nadie murió esta noche."
        );
    }
}