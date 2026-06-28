package roles.ciudadanos;

import partida.ContadorDeBandos;

public class Ciudadano extends roles.RolCiudadano {

    @Override
    public String nombre() {
        return "Ciudadano";
    }

    @Override
    public void agruparseEn(ContadorDeBandos contador) {
        contador.contarCiudadano();
    }

    @Override
    public boolean esSospechoso() {
        // Un ciudadano común nunca es sospechoso para el Detective
        return false;
    }
}
