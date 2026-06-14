package roles.ciudadanos;

import nocturno.RegistroNocturno;
import nocturno.ProteccionNocturna;
import partida.ContadorDeBandos;
import jugadores.Jugador;

public class Medico extends roles.RolCiudadano implements roles.RolNocturno {
    private Jugador ultimoProtegido;

    public void setObjetivo(Jugador objetivo) {
        this.objetivo = objetivo;
    }

    @Override
    public void ejecutarAccionNocturna(RegistroNocturno contexto) {
        // Validamos que el objetivo exista y siga con vida para poder protegerlo
        if (objetivo != null && objetivo.estaVivo()) {

            // Regla opcional del juego (por si la piden): No proteger al mismo dos veces seguidas
            if (!objetivo.equals(ultimoProtegido)) {
                ProteccionNocturna proteccion = new ProteccionNocturna(objetivo, null);
                contexto.registrarProteccion(proteccion);
                this.ultimoProtegido = objetivo;
            }
        }
    }

    @Override
    public void agruparseEn(ContadorDeBandos contador) {
        contador.contarCiudadano();
    }

    @Override
    public boolean esSospechoso() {
        return false; // Es un ciudadano inocente
    }
}