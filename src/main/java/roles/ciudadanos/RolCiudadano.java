package roles.ciudadanos;

import roles.Rol;

public abstract class RolCiudadano extends Rol {

    @Override
    public String bando() {
        return "Ciudadano";
    }
}
