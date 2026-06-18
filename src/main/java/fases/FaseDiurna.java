package fases;

import partida.Partida;
import partida.ResultadoPartida;
import jugadores.Jugador;
import votacion.Votacion;
import votacion.ResultadoVotacion;
import votacion.MecanismoEmpate;
import votacion.MecanismoSinEliminacion;
import java.util.Optional;
import java.util.List;

public class FaseDiurna extends Fase {

    private MecanismoEmpate mecanismoEmpate;

    public FaseDiurna() {
        //por defecto, sin eliminación, pero se puede settear otro (Ballotage)
        this.mecanismoEmpate = new MecanismoSinEliminacion();
    }

    public void setMecanismoEmpate(MecanismoEmpate mecanismo) {
        this.mecanismoEmpate = mecanismo;
    }

    @Override
    public void ejecutar(Partida partida) {
        for (Jugador jugador : partida.getJugadores()) {
            jugador.ejecutarTurnoDiurno(partida);
        }

        if (partida.verificarVictoria().esTerminal()) return;

        Votacion votacion = new Votacion();
        for (Jugador jugador : partida.getJugadores()) {
            jugador.votarEn(votacion);
        }

        ResultadoVotacion resultado = votacion.calcularResultado();
        Optional<Jugador> expulsado = resultado.obtenerExpulsado();

        //integramos el empate
        if (expulsado.isEmpty()) {
            List<Jugador> empatados = votacion.obtenerEmpatados();
            if (empatados.size() > 1) {
                expulsado = this.mecanismoEmpate.resolver(empatados, votacion);
            }
        }

        if (expulsado.isPresent()) {
            Jugador aEliminar = expulsado.get();
            partida.eliminarJugador(aEliminar);
            //la revelación de la carta de 'aEliminar' puede notificarse a la interfaz aca
        }
    }

    @Override
    public Fase siguienteFase() {
        return new FaseNocturna();
    }
}
