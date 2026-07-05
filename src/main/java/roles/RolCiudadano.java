package roles;

public abstract class RolCiudadano extends Rol {

    @Override
    public boolean esCiudadano() {
        return true;
    }

    @Override
    public boolean esAliadoDe(
            Rol otro) {

        return otro.esCiudadano();
    }
}

