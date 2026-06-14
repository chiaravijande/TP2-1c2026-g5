package mazo;

import roles.Rol;
import roles.ciudadanos.Detective;
import roles.ciudadanos.Medico;

import java.util.List;

public class ConfiguracionMazoDe7a9 extends ConfiguracionMazo {

    @Override
    protected void agregarRolesEspeciales(List<Rol> roles) {
        roles.add(new Detective());
        roles.add(new Medico());
    }

    @Override
    protected int cantidadMafiosos() {return 2;}
}