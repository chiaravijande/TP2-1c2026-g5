package votacion;
import jugadores.Jugador;
import java.util.List;
import java.util.Optional;

public interface MecanismoEmpate {

    Optional<Jugador> resolver(List<Jugador> empatados, Votacion votacion);

}