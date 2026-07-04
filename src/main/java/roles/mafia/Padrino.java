package roles.mafia;

public class Padrino extends Mafioso {

    @Override
    public String nombre() {
        return "Padrino";
    }

    @Override
    public boolean esSospechoso() {
        return false;
    }

    @Override
    public boolean tienePrioridadDeDesempate() {
        return true;
    }
}