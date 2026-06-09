package roles.ciudadanos;

import roles.Bando;
import roles.Rol;

public abstract class RolCiudadano extends Rol {

    @Override
    public Bando bando() {
        return Bando.CIUDADANOS;
    }
}
