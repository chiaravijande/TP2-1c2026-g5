package roles.mafia;

public class Padrino extends roles.Mafioso {
    // el Padrino (jefe de la mafia) tiene el poder pasivo de engañar al Detective.

    @Override
    public boolean esSospechoso() {
        // Miente y aparece como inocente ante la investigación
        return false;
    }
}
