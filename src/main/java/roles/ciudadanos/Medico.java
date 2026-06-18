package roles.ciudadanos;

import nocturno.RegistroNocturno;
import nocturno.ProteccionNocturna;
import partida.ContadorDeBandos;
import jugadores.Jugador;
import roles.RolCiudadano;
import roles.RolNocturno;

public class Medico extends RolCiudadano implements RolNocturno {

    private Jugador ultimoProtegido;

    @Override
    public void ejecutarAccionNocturna(RegistroNocturno contexto) {

        //valida que haya un objetivo y siga vivo
        if (this.objetivo == null || !this.objetivo.estaVivo()) {
            return;
        }

        //valida que no se protega al mismo jugador dos noches seguidas
        if (this.objetivo.equals(this.ultimoProtegido)) {
            return; //corta la ejecucion, la protección se rechaza
        }

        //creamos y registramos la protección
        ProteccionNocturna proteccion = new ProteccionNocturna(this.objetivo, null);
        contexto.registrarProteccion(proteccion);

        //actualizamos el historial
        this.ultimoProtegido = this.objetivo;
    }

    @Override
    public void agruparseEn(ContadorDeBandos contador) {
        contador.contarCiudadano();
    }

    @Override
    public boolean esSospechoso() {
        return false; // Es un ciudadano inocente
    }
}