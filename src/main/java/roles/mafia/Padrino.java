package roles.mafia;

import roles.Bando;

public class Padrino extends Mafioso {

    @Override
    public Bando aparienciaParaDetective() {

        return Bando.CIUDADANOS;
    }
}
