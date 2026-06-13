package roles.mafia;

public class Padrino extends roles.Mafioso {

    // No hace falta declarar que implementa RolNocturno ni reescribir bando()
    // o agruparseEn(), porque todo eso ya lo hereda perfectamente de Mafioso.

    /**
     * El Padrino (jefe de la mafia) tiene el poder pasivo de engañar al Detective.
     * Es el ÚNICO método que necesitamos sobrescribir según marca el diagrama.
     */
    @Override
    public boolean esSospechoso() {
        // Miente y aparece como inocente ante la investigación
        return false;
    }
}
