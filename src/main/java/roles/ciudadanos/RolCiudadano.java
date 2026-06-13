package roles;

public abstract class RolCiudadano extends Rol {
    @Override
    public Bando bando() {
        return Bando.CIUDADANOS;
    }
}
