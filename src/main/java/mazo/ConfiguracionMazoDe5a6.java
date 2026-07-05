package mazo;

import roles.Rol;
import roles.ciudadanos.Detective;
import roles.ciudadanos.Medico;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class ConfiguracionMazoDe5a6 extends ConfiguracionMazo {

    private static final List<Supplier<Rol>> ROLES_CANDIDATOS = List.of(
            Detective::new,
            Medico::new
    );

    @Override
    protected void agregarRolesEspeciales(List<Rol> roles) {
        int indice = new Random().nextInt(ROLES_CANDIDATOS.size());
        roles.add(ROLES_CANDIDATOS.get(indice).get());
    }

    @Override
    protected int cantidadMafiosos() {return 1;}
}