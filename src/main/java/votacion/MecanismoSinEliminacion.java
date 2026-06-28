package votacion;

import jugadores.Jugador;
import java.util.List;
import java.util.Optional;

public class MecanismoSinEliminacion implements MecanismoEmpate {

    @Override
    public Optional<Jugador> resolver(List<Jugador> empatados, Votacion votacionOriginal) {
        //al no haber eliminación en caso de empate, nadie es expulsado.
        return Optional.empty();
    }
}