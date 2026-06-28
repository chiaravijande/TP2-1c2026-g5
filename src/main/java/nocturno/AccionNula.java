package nocturno;

public class AccionNula
        implements AccionNocturna {

    @Override
    public void ejecutar(
            RegistroNocturno registro
    ) {
        // no hace nada
    }
}