package mazo;

import roles.Rol;
import roles.ciudadanos.Ciudadano;
import roles.Mafioso;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfiguracionMazo {

    public static ConfiguracionMazo para(int cantidadJugadores) {
        validarCantidadDeJugadores(cantidadJugadores);
        if (cantidadJugadores >= 10) return new ConfiguracionMazoDe10a12();
        if (cantidadJugadores >= 7)  return new ConfiguracionMazoDe7a9();
        return new ConfiguracionMazoDe5a6();
    }

    public List<Rol> generarRoles(int cantidadJugadores) {
        List<Rol> roles = new ArrayList<>();
        generarMafiosos(roles, cantidadJugadores);
        agregarRolesEspeciales(roles);
        completarConCiudadanos(roles, cantidadJugadores);
        return roles;
    }

    protected abstract void agregarRolesEspeciales(List<Rol> roles);

    protected abstract int cantidadMafiosos();

    private void generarMafiosos(List<Rol> roles, int cantidadJugadores) {
        int cantidad = cantidadMafiosos();
        for (int i = 0; i < cantidad; i++) {
            roles.add(new Mafioso());
        }
    }

    private void completarConCiudadanos(List<Rol> roles, int cantidadJugadores) {
        while (roles.size() < cantidadJugadores) {
            roles.add(new Ciudadano());
        }
    }

    private static void validarCantidadDeJugadores(int cantidadJugadores) {
        if (cantidadJugadores < 5 || cantidadJugadores > 12) {
            throw new CantidadDeJugadoresInvalidaException(cantidadJugadores);
        }
    }
}