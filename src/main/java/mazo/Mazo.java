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

    public void repartir(List<Jugador> jugadores) {
        List<Rol> roles = configuracion.generarRoles(jugadores.size());

        mezclador.mezclar(roles);

        for(int i = 0; i <  jugadores.size(); i++) {
            jugadores.get(i).setRol(roles.get(i));
        }
    }

}