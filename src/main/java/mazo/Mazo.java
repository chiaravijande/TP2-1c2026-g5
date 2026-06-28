package mazo;

import jugadores.Jugador;
import roles.Rol;

import java.util.Collections;
import java.util.List;

public class Mazo {

    private final ConfiguracionMazo configuracion;
    private final MezcladorMazo mezclador;

    public Mazo (ConfiguracionMazo configuracion) {
        this.configuracion = configuracion;
        this.mezclador = new MezcladorMazo();
    }

    /*
     Constructor de sobrecarga para testear mediante inyección de dependencia 
    */
    public Mazo (ConfiguracionMazo configuracion, MezcladorMazo mezclador) {
        this.configuracion = configuracion;
        this.mezclador = mezclador;
    }

    public List<Rol> repartir(int cantidadJugadores) {
        List<Rol> roles = configuracion.generarRoles(cantidadJugadores);
        mezclador.mezclar(roles);
        return roles;
    }

}