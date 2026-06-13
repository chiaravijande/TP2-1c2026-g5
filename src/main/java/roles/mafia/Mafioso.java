package roles;

import nocturno.AtaqueNocturno;
import nocturno.RegistroNocturno;
import partida.ContadorDeBandos;

public class Mafioso extends Rol implements RolNocturno {

    @Override
    public void ejecutarAccionNocturna(RegistroNocturno contexto) {

        //1-Verifica que haya un objetivo seleccionado
        if (this.objetivo != null) {

            //2-valida que la victima este viva
            if (!this.objetivo.estaVivo()) {
                return; //se rechaza el ataque
            }

            //3-valida que la victima no sea otro Mafioso
            // (Agregamos el != null por si en los tests el rol no está mockeado)
            if (this.objetivo.getRol() != null && this.objetivo.getRol().bando() == Bando.MAFIA) {
                return; // Cortamos la ejecución, el ataque se rechaza
            }

            //el objetivo es valido
            AtaqueNocturno ataque = new AtaqueNocturno(null, this.objetivo);
            contexto.registrarAtaque(ataque);
        }
    }

    @Override
    public void agruparseEn(ContadorDeBandos contador) {
        contador.contarMafioso();
    }

    @Override
    public boolean esSospechoso() {
        return true;
    }

    @Override
    public Bando bando() {
        return Bando.MAFIA;
    }
}