package roles.mafia;

import jugadores.Jugador;
import nocturno.AccionNocturna;
import nocturno.VotoMafia;
import partida.ContadorDeBandos;
import roles.Rol;

import java.util.Optional;

public class Mafioso extends Rol {

    @Override
    public String nombre() {
        return "Mafioso";
    }

    @Override
    public Optional<AccionNocturna> prepararAccion(
            Jugador actor,
            Optional<Jugador> objetivo) {

        return objetivo.map(jugador -> new VotoMafia(actor, jugador));
    }

    @Override
    public void agruparseEn(
            ContadorDeBandos contador) {

        contador.contarMafioso();
    }

    @Override
    public boolean esSospechoso() {
        return true;
    }

    @Override
    public boolean esMafia() {
        return true;
    }

    @Override
    public boolean esAliadoDe(
            Rol otro) {

        return otro.esMafia();
    }
}