package roles.ciudadanos;

import jugadores.Jugador;
import nocturno.AccionNocturna;
import nocturno.ProteccionNocturna;
import partida.ContadorDeBandos;
import roles.RolCiudadano;

import java.util.Optional;

public class Medico extends RolCiudadano {

    private Jugador ultimoProtegido;

    @Override
    public String nombre() {
        return "Médico";
    }
    
    @Override
    public Optional<AccionNocturna> prepararAccion(
            Jugador actor,
            Optional<Jugador> objetivo) {

        return objetivo.flatMap(jugador -> {
            if (jugador == ultimoProtegido) {
                return Optional.empty();
            }
            ultimoProtegido = jugador;
            return Optional.of(new ProteccionNocturna(jugador));
        });
    }

    @Override
    public void agruparseEn(
            ContadorDeBandos contador) {

        contador.contarCiudadano();
    }

    @Override
    public boolean esSospechoso() {
        return false;
    }
}