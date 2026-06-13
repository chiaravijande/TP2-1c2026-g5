package roles.ciudadanos;

import partida.Partida;
import partida.ContadorDeBandos;

public class Sheriff extends roles.RolCiudadano implements roles.RolDiurno {

    private boolean yaSeRevelo;

    // el Sheriff arranca con su identidad oculta
    public Sheriff() {
        this.yaSeRevelo = false;
    }

    @Override
    public void ejecutarAccionDiurna(Partida partida) {

        // Solo puede revelarse una vez por partida
        if (!this.yaSeRevelo) {

            // Se marca como revelado
            this.yaSeRevelo = true;

            // Acá más adelante la interfaz o la partida
            // podrían anunciar públicamente que este jugador es Sheriff.
        }
    }

    public boolean estaRevelado() {
        return this.yaSeRevelo;
    }

    @Override
    public void agruparseEn(ContadorDeBandos contador) {
        contador.contarCiudadano();
    }

    @Override
    public boolean esSospechoso() {
        return false;
    }
}
