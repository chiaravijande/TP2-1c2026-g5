package roles;

import jugadores.Jugador;
import nocturno.AccionNocturna;
import partida.ContadorDeBandos;
import nocturno.ResultadoInvestigacion;
import java.util.Optional;

public abstract class Rol {

    public String nombre() {
        return "Rol";
    }

    public Optional<AccionNocturna> prepararAccion(
            Jugador actor,
            Optional<Jugador> objetivo) {

        return Optional.empty();
    }

    public abstract void agruparseEn(
            ContadorDeBandos contador);

    public abstract boolean esSospechoso();

    public boolean esMafia() {
        return false;
    }

    public boolean esCiudadano() {
        return false;
    }

    public abstract boolean esAliadoDe(
            Rol otro
    );

    public boolean revelaInformacion() {
        return false;
    }

    public String investigar(Jugador objetivo) {
        return "";
    }

    public boolean tieneAccionNocturna() {
        return false;
    }

    public boolean tienePrioridadDeDesempate() {
        return false;
    }
    public Optional<ResultadoInvestigacion> revelarInvestigacion() {
        return Optional.empty();
    }

    public boolean puedeRevelarInvestigacion() {
        return false;
    }
}