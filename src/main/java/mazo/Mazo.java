package mazo;

import jugadores.Jugador;
import roles.Rol;

import java.util.Collections;
import java.util.List;

public class Mazo {

    private final ConfiguracionMazo configuracion;

    public Mazo (ConfiguracionMazo configuracion) {
        this.configuracion = configuracion;
    }

    public void repartir(List<Jugador> jugadores) {
        List<Rol> roles = configuracion.generarRoles(jugadores.size());

        Collections.shuffle(roles);

        for(int i = 0; i <  jugadores.size(); i++) {
            jugadores.get(i).setRol(roles.get(i));
        }
    }

}