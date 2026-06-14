package mazo;

import roles.Rol;
import roles.ciudadanos.Detective;
import roles.ciudadanos.Medico;
import roles.ciudadanos.Sheriff;
import roles.mafia.Padrino;

import java.util.List;

public class ConfiguracionMazoDe10a12 extends ConfiguracionMazo {

    @Override
    protected void agregarRolesEspeciales(List<Rol> roles) {
        roles.add(new Padrino());
        roles.add(new Detective());
        roles.add(new Medico());
        roles.add(new Sheriff());
    }

    @Override
    protected int cantidadMafiosos() {return 2;}
}